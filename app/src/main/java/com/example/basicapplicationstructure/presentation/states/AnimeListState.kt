package com.example.basicapplicationstructure.presentation.states

import com.example.basicapplicationstructure.presentation.AnimeData

data class AnimeListState(
    val isLoading: Boolean = false,
    val animeList: List<AnimeData> = emptyList<AnimeData>(),
)
