package com.example.basicapplicationstructure.network

import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET("/sample")
    suspend fun getSampleDataFromServer(): Response<String>
}