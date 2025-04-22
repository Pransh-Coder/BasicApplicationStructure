package com.example.basicapplicationstructure.network

import com.example.basicapplicationstructure.data.Movies
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET("f20e-a836-4993-9606")
    suspend fun getMoviesListFromServer(): Response<List<Movies>>
}