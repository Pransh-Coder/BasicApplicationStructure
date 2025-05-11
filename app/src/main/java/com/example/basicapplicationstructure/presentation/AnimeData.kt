package com.example.basicapplicationstructure.presentation

import com.example.basicapplicationstructure.data.Images

data class AnimeData(
    val mal_id: String,
    val title: String,
    val episodes: Int,
    val score: Double,
    val images: Images,

)
