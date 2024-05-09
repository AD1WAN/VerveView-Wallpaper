package com.example.wallpaperapp.Presentation.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wallpaperapp.Presentation.ViewModel.wallPaperViewModel
import com.example.wallpaperapp.Presentation.WallPaperUiState
import com.example.wallpaperapp.Presentation.adapter.ImagesRecyclerViewAdapter
import com.example.wallpaperapp.Presentation.adapter.ItemOnClickListener
import com.example.wallpaperapp.R
import com.example.wallpaperapp.databinding.ActivityMainBinding
import com.example.wallpaperapp.domain.entity.WallpaperLink
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ItemOnClickListener {
    private lateinit var binding: ActivityMainBinding
    private val wallpaperviewmodel: wallPaperViewModel by viewModels()
    // private lateinit var wallpaperAdapter: ImagesRecyclerViewAdapter
    private lateinit var wallpaperAdapter: ImagesRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        setViews()
        collectUIState()
        wallpaperviewmodel.fetchWallpapers()


    }

    fun setViews() {

        binding.imagesRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(this@MainActivity,2)
        }



    }

    fun collectUIState() {
        //coroutine
        //Main heheh or ui
        //Defaukt
        //Io
        lifecycleScope.launch(Dispatchers.Main) {
            wallpaperviewmodel.wallpaperList.collect() { wallpaperUiState ->
                when (wallpaperUiState) {
                    is WallPaperUiState.Loading -> {
                        //progress bar
                        binding.progressBar.visibility = View.VISIBLE
                        Toast.makeText(
                            this@MainActivity,
                            "Wallpapers are currently Loading",
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                    is WallPaperUiState.EmptyList -> {
                        //empty
                        binding.progressBar.visibility = View.VISIBLE
                        Toast.makeText(
                            this@MainActivity,
                            "Wallpapers are currently empty",
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                    is WallPaperUiState.Success -> {
                        //update recyclerView
                        binding.progressBar.visibility = View.GONE
                        //update the recyclerView

                        populateDataInRecyclerView(wallpaperUiState.data)


                    }

                    is WallPaperUiState.Error -> {
                        //toast error
                        Toast.makeText(this@MainActivity, "Error Occured", Toast.LENGTH_SHORT)
                            .show()

                    }
                }

            }


        }


    }

    fun populateDataInRecyclerView(list: List<WallpaperLink>) {
        wallpaperAdapter = ImagesRecyclerViewAdapter(list, this)
        binding.imagesRecyclerView.adapter = wallpaperAdapter


    }

    override fun onclickImage(wallpaperLinks: String) {
        TODO("Not yet implemented")
    }
}



