package com.example.basicapplicationstructure.presentation

import com.example.basicapplicationstructure.data.MoviesMapper
//todo it should be a sealed class ? but why
data class MoviesState(
    val isLoading: Boolean = false,
    val moviesList: List<MoviesMapper>? = emptyList<MoviesMapper>(),
)
