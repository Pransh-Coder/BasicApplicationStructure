package com.example.basicapplicationstructure.presentation.states

import com.example.basicapplicationstructure.presentation.AnimeDetailData

data class AnimeDetailState(
    val isLoading: Boolean = false,
    val animeDetailsData: AnimeDetailData? = null,
)
