package com.example.basicapplicationstructure.network

import com.example.basicapplicationstructure.data.remoteDataSource.User
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET("/sample")
    suspend fun getUserDataById(id: String): Response<User>
}