package com.example.basicapplicationstructure.data

import com.example.basicapplicationstructure.network.NetworkResponse
import kotlinx.coroutines.flow.Flow

// why interface?
interface MoviesRepositoryInterface {

    fun getMoviesList(): Flow<NetworkResponse<List<MoviesMapper>>>      //why flow?
}