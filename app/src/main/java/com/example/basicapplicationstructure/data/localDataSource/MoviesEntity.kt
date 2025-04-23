package com.example.basicapplicationstructure.data.localDataSource

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MoviesEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val year: String,
    val rated: String,
    val released: String,
    val runtime: String,
    val actors: String,
    val language: String,
    val imdbRating: String,
    val type: String,
    val image: String
)
