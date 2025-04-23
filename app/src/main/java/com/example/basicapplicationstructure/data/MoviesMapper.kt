package com.example.basicapplicationstructure.data

import com.google.gson.annotations.SerializedName

// todo It should be in presentation layer
// don't copy
// Naming conventions (can be improved rename it to Movies)
data class MoviesMapper(
    val title: String,
    val year: String,
    val rated: String,
    val released: String,
    val runtime: String,
    val actors: String,
    val language: String,
    val imdbRating: String,
    val type: String,
    val images: List<String>?,
)
