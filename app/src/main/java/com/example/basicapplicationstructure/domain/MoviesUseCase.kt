package com.example.basicapplicationstructure.domain

import com.example.basicapplicationstructure.data.MoviesMapper
import com.example.basicapplicationstructure.data.MoviesRepositoryInterface
import com.example.basicapplicationstructure.network.NetworkResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class MoviesUseCase @Inject constructor(private val moviesRepositoryInterface: MoviesRepositoryInterface) {

    // it should handle actual business logic like saving the data in db or retriving it

    //how to get the data from where?

    operator fun invoke(): Flow<NetworkResponse<List<MoviesMapper>>>{       //Movies  MovieMapper - Movies || Movies - MoviesResponse
        //its work of presentation layer
       return moviesRepositoryInterface.getMoviesList().transform {
           val sortedListByName = it.data?.sortedBy { it.title }
           when(it){
               is NetworkResponse.Loading -> {
                   emit(NetworkResponse.Loading())
               }
               is NetworkResponse.Success -> {
                   emit(NetworkResponse.Success(data = sortedListByName ?: emptyList()))
               }
               is NetworkResponse.Error -> {
                   emit(NetworkResponse.Error(errorMessage = it.errorMessage))
               }
           }
       }
    }

}