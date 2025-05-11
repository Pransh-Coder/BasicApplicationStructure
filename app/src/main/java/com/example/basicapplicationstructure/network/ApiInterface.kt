package com.example.basicapplicationstructure.network

import com.example.basicapplicationstructure.data.AnimeDetailResponse
import com.example.basicapplicationstructure.data.AnimeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {

    @GET("top/anime")
    suspend fun getAnimeListFromServer(): Response<AnimeResponse>

    @GET("anime/{id}")
    suspend fun getAnimeDetailBasedOnId(@Path("id") id: String): Response<AnimeDetailResponse>
}