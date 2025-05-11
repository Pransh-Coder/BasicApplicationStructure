package com.example.basicapplicationstructure.domain

import com.example.basicapplicationstructure.data.AnimeRepositoryInterface
import com.example.basicapplicationstructure.network.Resource
import com.example.basicapplicationstructure.presentation.AnimeDetailData
import javax.inject.Inject

class GetAnimeDetailUseCase @Inject constructor(private val repositoryInterface: AnimeRepositoryInterface) {

    suspend fun invoke(id: String): Resource<AnimeDetailData>{
        val animeDetailsNetworkResponse = repositoryInterface.getAnimeDetailByIdNetwork(id = id)
        when(animeDetailsNetworkResponse){
            is Resource.Success -> {

                val animeDetailsRes = animeDetailsNetworkResponse.data.data

                val animeConvertedDetails = AnimeDetailData(
                    title = animeDetailsRes.title,
                    trailer = animeDetailsRes.trailer,
                    synopsis = animeDetailsRes.synopsis,
                    genres = animeDetailsRes.genres,
                    episodes = animeDetailsRes.episodes,
                    score = animeDetailsRes.score,
                    members = animeDetailsRes.members,
                    images = animeDetailsRes.images
                )

                return Resource.Success(data = animeConvertedDetails)
            }
            is Resource.NoInternetError -> {
                return Resource.NoInternetError()
            }
            is Resource.Error -> {
                return Resource.Error(errorMessage = animeDetailsNetworkResponse.errorMessage)
            }
        }
    }
}