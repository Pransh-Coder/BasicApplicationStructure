package com.example.basicapplicationstructure.domain

import com.example.basicapplicationstructure.data.AnimeRepositoryInterface
import com.example.basicapplicationstructure.network.Resource
import com.example.basicapplicationstructure.presentation.AnimeData
import javax.inject.Inject

class GetAnimeListDataUseCase @Inject constructor(private val repositoryInterface: AnimeRepositoryInterface) {

    suspend fun invoke(): Resource<List<AnimeData>> {
        val networkResponse = repositoryInterface.getAllAnimeListFromNetwork()
        when (networkResponse) {
            is Resource.Success -> {
                val animeListFromNetwork = networkResponse.data.data.map {
                    AnimeData(
                        mal_id = it.mal_id,
                        title = it.title,
                        it.episodes,
                        score = it.score,
                        it.images
                    )
                }
                return Resource.Success(data = animeListFromNetwork)
            }

            is Resource.NoInternetError -> {
                return Resource.NoInternetError()
            }

            is Resource.Error -> {
                return Resource.Error(errorMessage = networkResponse.errorMessage)
            }
        }
    }
    companion object{
        private const val TAG = "GetAnimeListDataUseCase"
    }
}