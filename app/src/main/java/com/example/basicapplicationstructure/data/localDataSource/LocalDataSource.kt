package com.example.basicapplicationstructure.data.localDataSource

import com.example.basicapplicationstructure.data.remoteDataSource.MoviesResponse
import com.example.basicapplicationstructure.network.Resource
import com.example.basicapplicationstructure.presentation.MoviesData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val dao: MoviesDao) {

    fun getMoviesListFromDB(): Flow<Resource<List<MoviesEntity>>> {
        return flow {
            try {
                val moviesFromDbList = dao.getAllMoviesList()
                emit(Resource.Success(data = moviesFromDbList))
            } catch (ex: Exception){
                emit(Resource.Error(errorMessage = "Excep in DB $ex"))
            }
        }
    }

    suspend fun insertAllMoviesInDatabase(moviesMappedList: List<MoviesResponse>?) {
        val moviesEntityList = moviesMappedList?.map {
            MoviesEntity(
                title = it.title,
                year = it.year,
                rated = it.rated,
                released = it.released,
                runtime = it.runtime,
                actors = it.actors,
                language = it.language,
                imdbRating = it.imdbRating,
                type = it.type,
                image = it.images?.getOrNull(0)?:""
            )
        } ?: emptyList()

        dao.insertAllMovies(moviesEntityList)
    }
}