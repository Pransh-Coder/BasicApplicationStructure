package com.example.basicapplicationstructure.data

import com.example.basicapplicationstructure.data.localDataSource.MoviesEntity
import com.example.basicapplicationstructure.data.remoteDataSource.MoviesResponse
import com.example.basicapplicationstructure.network.Resource
import kotlinx.coroutines.flow.Flow

// why interface?
//todo renaming it
interface MoviesRepositoryInterface {

    suspend fun getMoviesListFromNetwork(): Resource<List<MoviesResponse>>      //why flow?

    suspend fun getMoviesListFromLocalDB() : Flow<Resource<List<MoviesEntity>>>
}