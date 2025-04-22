package com.example.basicapplicationstructure.domain

import com.example.basicapplicationstructure.data.MoviesMapper
import com.example.basicapplicationstructure.data.MoviesRepositoryInterface
import com.example.basicapplicationstructure.network.NetworkResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class MoviesUseCase @Inject constructor(private val moviesRepositoryInterface: MoviesRepositoryInterface) {

    operator fun invoke(): Flow<NetworkResponse<List<MoviesMapper>>>{
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