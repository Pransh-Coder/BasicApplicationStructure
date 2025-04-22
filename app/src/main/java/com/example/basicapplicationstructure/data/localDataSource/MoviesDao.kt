package com.example.basicapplicationstructure.data.localDataSource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query

@Dao
interface MoviesDao {

    @Query("Select * from moviesentity")
    suspend fun getAllMoviesList() : List<MoviesEntity>

    @Insert(onConflict = REPLACE)
    suspend fun insertAllMovies(moviesEntity: List<MoviesEntity>)
}