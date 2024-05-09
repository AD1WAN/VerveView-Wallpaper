package com.example.wallpaperapp.data.api
import com.example.wallpaperapp.Utils.Constants.BASE_URL

import com.example.wallpaperapp.data.api.model.PicSumItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

//RETROFIT SHIT
interface PickSumApi {

    @GET(BASE_URL)
   fun getWallpaperImages(
        @Query("page") page: Int, @Query("limit") limit: Int = 100
    ): List<PicSumItem>?
}