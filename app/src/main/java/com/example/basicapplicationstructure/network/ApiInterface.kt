package com.example.basicapplicationstructure.network

import com.example.basicapplicationstructure.data.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("searchJSON")
    suspend fun getSearchResultsBasedOnNames(
        @Query("name_startsWith") nameStartsWith: String,
        @Query("maxRows") maxRows: Int,
        @Query("username") userName: String,
    ): Response<SearchResponse>
}