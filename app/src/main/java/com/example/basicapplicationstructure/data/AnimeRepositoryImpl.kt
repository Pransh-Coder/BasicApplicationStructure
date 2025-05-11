package com.example.basicapplicationstructure.data

import com.example.basicapplicationstructure.data.remoteDataSource.RemoteDataSource
import com.example.basicapplicationstructure.network.Resource
import javax.inject.Inject

class AnimeRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource): AnimeRepositoryInterface {

    override suspend fun getAllAnimeListFromNetwork(): Resource<AnimeResponse> {
        return remoteDataSource.getAnimeListFromNetwork()
    }

    override suspend fun getAnimeDetailByIdNetwork(id: String): Resource<AnimeDetailResponse> {
        return remoteDataSource.getAnimeDetailByIdNetwork(id = id)
    }

}