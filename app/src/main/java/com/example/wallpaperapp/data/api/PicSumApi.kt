package com.example.wallpaperapp.data.api

import android.telecom.Call
import com.example.wallpaperapp.Utils.Constants.BASE_URL
import com.example.wallpaperapp.data.api.model.PicSumApiItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface PicSumApi {
    @GET(BASE_URL)
    suspend fun getWallpaperImages(
        @Query("page") page: Int = 1, @Query("limit") limit: Int= 100
    ):  List<PicSumApiItem>?

}