package com.example.basicapplicationstructure.data

import android.util.Log
import com.example.basicapplicationstructure.data.localDataSource.MoviesDao
import com.example.basicapplicationstructure.data.localDataSource.MoviesEntity
import com.example.basicapplicationstructure.network.ApiInterface
import com.example.basicapplicationstructure.network.NetworkResponse
import com.example.basicapplicationstructure.network.invokeOnStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

//Remote data source & Local data source Sigle responsibilty
class MoviesRepositoryImpl @Inject constructor(
    private val apiInterface: ApiInterface,
    private val dao: MoviesDao,
) : MoviesRepositoryInterface {

    //not giving concrete implementation

    override fun getMoviesList(): Flow<NetworkResponse<List<MoviesMapper>>> {
        //why flow ?
        return flow {
            //todo remove it
            emit(NetworkResponse.Loading()) // its a UI component
            //get the data from db 1st & if it is not there then get from network
            //it is having 2 source of truth
            // getting data from network should not be flow ? becoz its not stream of data

            try {
                val response = apiInterface.getMoviesListFromServer()
                response.code().invokeOnStatus(
                    onSuccess = {
                        val moviesMappedList = response.body()?.map {
                            MoviesMapper(
                                title = it.title,
                                year = it.year,
                                rated = it.rated,
                                released = it.released,
                                runtime = it.runtime,
                                actors = it.actors,
                                language = it.language,
                                imdbRating = it.imdbRating,
                                type = it.type,
                                images = it.images
                            )
                        }
                        emit(NetworkResponse.Success(data = moviesMappedList ?: emptyList()))

                        //todo it should not be here in a separate class
                        insertAllMoviesInDatabase(moviesMappedList)
                    },
                    onError = {
                        emit(NetworkResponse.Error(response.errorBody().toString()))
                    }
                )

            } catch (ex: Exception) {
                emit(NetworkResponse.Error("Something went wrong! $ex"))

                emit(NetworkResponse.Loading())

                //alot of business logic is here
                if (dao.getAllMoviesList().isNotEmpty()) {
                    Log.e(TAG, "getMoviesList from Database: ")
                    val moviesListFromDb = dao.getAllMoviesList()

                    val convertedList = moviesListFromDb.map {
                        MoviesMapper(
                            title = it.title,
                            year = it.year,
                            rated = it.rated,
                            released = it.released,
                            runtime = it.runtime,
                            actors = it.actors,
                            language = it.language,
                            imdbRating = it.imdbRating,
                            type = it.type,
                            images = emptyList()
                        )
                    }

                    emit(NetworkResponse.Success(data = convertedList))
                }
            }
        }
    }

    private suspend fun insertAllMoviesInDatabase(moviesMappedList: List<MoviesMapper>?) {
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
            )
        } ?: emptyList()

        dao.insertAllMovies(moviesEntityList)
    }

    companion object {
        private const val TAG = "MoviesRepositoryImpl"
    }
}