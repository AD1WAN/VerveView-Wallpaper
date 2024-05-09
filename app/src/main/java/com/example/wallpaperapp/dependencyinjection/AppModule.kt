package com.example.wallpaperapp.dependencyinjection

import com.example.wallpaperapp.Utils.Constants.BASE_URL
import com.example.wallpaperapp.data.api.PicSumApi
import com.example.wallpaperapp.data.api.WallpaperRepositoryImpl
import com.example.wallpaperapp.domain.repository.WallpaperRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)

interface AppModule {

companion object{
    @Provides
    @Singleton
    fun provideRetrofit(
        // Potential dependencies of this type
    ): PicSumApi {
        return Retrofit.Builder()
         //   .baseUrl("https://example.com")
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(PicSumApi::class.java)

          /*  .build()
            .addConverterfactory(GsonConverterFactory.create())
            .create(PicSumApi::class.java)*/
    }
}
    @Binds
    @Singleton
    fun provideMainRepositoryImpl(repository: WallpaperRepositoryImpl): WallpaperRepository
}