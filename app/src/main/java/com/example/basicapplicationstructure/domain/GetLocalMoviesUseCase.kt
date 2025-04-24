package com.example.basicapplicationstructure.domain

import com.example.basicapplicationstructure.data.MoviesRepositoryInterface
import com.example.basicapplicationstructure.network.Resource
import com.example.basicapplicationstructure.presentation.MoviesData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetLocalMoviesUseCase @Inject constructor(private val moviesRepositoryInterface: MoviesRepositoryInterface) {

    suspend operator fun invoke(): Flow<Resource<List<MoviesData>>> {
        return moviesRepositoryInterface.getMoviesListFromLocalDB().map { resource ->
            when (resource) {
                is Resource.Error -> {
                    Resource.Error(resource.errorMessage)
                }

                is Resource.NoInternetException -> {
                    Resource.NoInternetException()
                }

                is Resource.Success -> {
                    val mappedData = resource.data.map { entity ->
                        MoviesData(
                            title = entity.title,
                            year = entity.year,
                            rated = entity.rated,
                            released = entity.released,
                            runtime = entity.runtime,
                            actors = entity.actors,
                            language = entity.language,
                            imdbRating = entity.imdbRating,
                            type = entity.type,
                            images = listOf(entity.image)
                        )
                    }

                    Resource.Success(mappedData)
                }
            }
        }
    }
}