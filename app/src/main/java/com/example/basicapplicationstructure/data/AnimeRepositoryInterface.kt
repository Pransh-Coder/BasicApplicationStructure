package com.example.basicapplicationstructure.data

import com.example.basicapplicationstructure.network.Resource

interface AnimeRepositoryInterface {

    suspend fun getAllAnimeListFromNetwork(): Resource<AnimeResponse>

    suspend fun getAnimeDetailByIdNetwork(id: String): Resource<AnimeDetailResponse>
}