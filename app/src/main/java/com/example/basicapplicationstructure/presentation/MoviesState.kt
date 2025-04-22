package com.example.basicapplicationstructure.presentation

import com.example.basicapplicationstructure.data.MoviesMapper

data class MoviesState(
    val isLoading: Boolean = false,
    val moviesList: List<MoviesMapper>? = emptyList<MoviesMapper>(),
)
