package com.example.basicapplicationstructure.data.localDataSource

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MoviesEntity::class], version = 1)
abstract class MoviesAppDatabase : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao
}