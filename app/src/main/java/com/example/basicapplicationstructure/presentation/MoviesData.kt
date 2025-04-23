package com.example.basicapplicationstructure.presentation

// todo It should be in presentation layer
// don't copy
// Naming conventions (can be improved rename it to Movies)
data class MoviesData(
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