package com.example.basicapplicationstructure.presentation

//todo it should be a sealed class ? but why
data class MoviesState(
    val isLoading: Boolean = false,
    val moviesList: List<MoviesData>? = emptyList<MoviesData>(),
)
