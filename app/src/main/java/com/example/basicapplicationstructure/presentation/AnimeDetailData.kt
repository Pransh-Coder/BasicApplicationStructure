package com.example.basicapplicationstructure.presentation

import com.example.basicapplicationstructure.data.Genres
import com.example.basicapplicationstructure.data.Images
import com.example.basicapplicationstructure.data.Trailer

data class AnimeDetailData(
    val title: String,
    val trailer: Trailer?,
    val images: Images,
    val synopsis: String,
    val genres: List<Genres>,
    val episodes: Int,
    val score: Double,
    val members: String //as cast Assumption
)
