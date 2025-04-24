package com.example.basicapplicationstructure.data.localDataSource

import androidx.room.Database
import androidx.room.RoomDatabase

// The database class must be abstract because "Room generates the actual code during compilation"

// You are defining the schema(table) and the DAO access points, but Room provides the actual database
// implementation behind the scenes.
@Database(entities = [MoviesEntity::class], version = 2)
abstract class MoviesAppDatabase : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao  // why abstract ?
}