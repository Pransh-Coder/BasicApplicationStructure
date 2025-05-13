package com.example.basicapplicationstructure.data.repository

import com.example.basicapplicationstructure.data.localDataSource.MoviesDao
import com.example.basicapplicationstructure.data.localDataSource.MoviesEntity

class FakeMoviesDaoImpl : MoviesDao {

    private val fakeMoviesListFromDB = mutableListOf<MoviesEntity>()

    override suspend fun getAllMoviesList(): List<MoviesEntity> {
        return fakeMoviesListFromDB
    }

    override suspend fun insertAllMovies(moviesEntityList: List<MoviesEntity>) {
        fakeMoviesListFromDB.addAll(moviesEntityList)
    }

    fun clearDataBase(){
        fakeMoviesListFromDB.clear()
    }
}