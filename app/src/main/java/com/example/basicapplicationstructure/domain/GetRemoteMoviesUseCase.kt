package com.example.basicapplicationstructure.domain

import com.example.basicapplicationstructure.presentation.MoviesData
import com.example.basicapplicationstructure.data.MoviesRepositoryInterface
import com.example.basicapplicationstructure.data.localDataSource.LocalDataSource
import com.example.basicapplicationstructure.network.Resource
import javax.inject.Inject

class GetRemoteMoviesUseCase @Inject constructor(
    private val moviesRepositoryInterface: MoviesRepositoryInterface,
    val localDataSource: LocalDataSource,
) {

    // it should handle actual business logic like saving the data in db or retriving it

    //how to get the data from where?

    suspend fun invoke(): Resource<List<MoviesData>> {       //Movies  MovieMapper - Movies || Movies - MoviesResponse

        //its work of presentation layer
        val networkResponse = moviesRepositoryInterface.getMoviesListFromNetwork()

        when(networkResponse){
            is Resource.Error -> {
                return Resource.Error(errorMessage = networkResponse.errorMessage)
            }
            is Resource.NoInternetException -> {
                return Resource.NoInternetException()
            }
            is Resource.Success -> {
                val mappedList = networkResponse.data.map {
                    MoviesData(
                        title = it.title,
                        year = it.year,
                        released = it.released,
                        rated = it.rated,
                        runtime = it.runtime,
                        actors = it.actors,
                        language = it.language,
                        imdbRating = it.imdbRating,
                        type = it.type,
                        images = it.images
                    )
                }

                localDataSource.insertAllMoviesInDatabase(moviesMappedList = networkResponse.data)

                return Resource.Success(data = mappedList)
            }
        }
    }
}