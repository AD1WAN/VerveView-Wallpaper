package com.example.wallpaperapp.presentation.view

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.wallpaperapp.presentation.adapter.ImagesRecyclerViewAdapter
import com.example.wallpaperapp.databinding.ActivityMainBinding
import com.example.wallpaperapp.domain.entity.WallpaperLink
import com.example.wallpaperapp.presentation.WallpaperUiState
import com.example.wallpaperapp.presentation.viewmodel.WallpaperViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


import com.example.wallpaperapp.presentation.adapter.ItemOnClickListener as ItemOnClickListener




@AndroidEntryPoint
class WallpaperActivity : AppCompatActivity(), ItemOnClickListener {

    private lateinit var binding: ActivityMainBinding
    private val wallpaperViewModel: WallpaperViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        setupViews()
        collectUiState()
        wallpaperViewModel.fetchWallpaper()

    }

    fun setupViews() {
        binding.imagesRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
        }
    }


    fun collectUiState() {
        lifecycleScope.launch(Dispatchers.Main) {
            wallpaperViewModel.wallpapaerList.collect { WallpaperUiState ->
                when (WallpaperUiState) {
                    is WallpaperUiState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        Toast.makeText(
                            this@WallpaperActivity,
                            "Wallpapers are Loading",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is WallpaperUiState.EmptyList -> {
                        binding.progressBar.visibility = View.VISIBLE
                        Toast.makeText(
                            this@WallpaperActivity,
                            "Wallpapers are currently Empty",
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                    is WallpaperUiState. Success -> {
                        binding.progressBar.visibility = View.GONE
                        populateDataInRecyclerView(WallpaperUiState.data)

                    }

                    is WallpaperUiState.Error -> {
                        Toast.makeText(
                            this@WallpaperActivity,
                            "Some Error Occured",
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                    else -> {}
                }

            }
        }

    }

    fun populateDataInRecyclerView(list: List<WallpaperLink>) {

        val wallpaperAdapter = ImagesRecyclerViewAdapter(list,this)
        binding.imagesRecyclerView.adapter = wallpaperAdapter


    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onClickImage(WallpapaerLinks: String) {

        val imageUrl = WallpapaerLinks

        GlobalScope.launch(Dispatchers.Main) {
            try {
                // Load the image using Glide on a background thread
                val bitmap: Bitmap = withContext(Dispatchers.IO) {
                    Glide.with(this@WallpaperActivity)
                        .asBitmap()
                        .load(imageUrl)
                        .submit()
                        .get()
                }

                // Set the wallpaper on the main thread
                setWallpaper(bitmap)
            } catch (e: Exception) {
                e.printStackTrace()
                // Handle errors here
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun setWallpaper(bitmap: Bitmap) {
        val wallpaperManager: WallpaperManager = WallpaperManager.getInstance(this@WallpaperActivity)

        try {
            wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM)
            Toast.makeText(this@WallpaperActivity, "Wallpaper Set Successfully", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this@WallpaperActivity, "Error Setting Wallpaper ", Toast.LENGTH_SHORT).show()
            // Handle errors here
        }
    }



}



























