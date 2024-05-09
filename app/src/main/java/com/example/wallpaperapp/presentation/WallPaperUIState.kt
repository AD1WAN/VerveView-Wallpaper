package com.example.wallpaperapp.Presentation

import com.example.wallpaperapp.domain.entity.WallpaperLink

sealed class WallPaperUiState{
    data object Loading : WallPaperUiState()
    data object EmptyList : WallPaperUiState()
    data class Success(val data: List<WallpaperLink>) : WallPaperUiState()
    data class Error(val message:String) : WallPaperUiState()

}
