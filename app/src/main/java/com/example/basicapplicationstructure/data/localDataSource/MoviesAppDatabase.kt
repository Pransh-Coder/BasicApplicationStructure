package com.example.basicapplicationstructure.data.localDataSource

import androidx.room.Database
import androidx.room.RoomDatabase

//study about the annotations what is the use of what
@Database(entities = [MoviesEntity::class], version = 2)
abstract class MoviesAppDatabase : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao  // why abstract ?
}