package com.example.basicapplicationstructure.domain

import com.example.basicapplicationstructure.data.SearchRepositoryInterface
import com.example.basicapplicationstructure.network.Resource
import com.example.basicapplicationstructure.presentation.SearchResults
import javax.inject.Inject

class GetSearchUseCase @Inject constructor(private val repositoryInterface: SearchRepositoryInterface) {

    suspend operator fun invoke(nameStartsWith: String, maxRows: Int, userName: String): Resource<List<SearchResults>> {
        val searchResults = repositoryInterface.getSearchResultsBasedOnNames(
            nameStartsWith = nameStartsWith,
            maxRows = maxRows,
            userName = userName
        )
        when (searchResults) {
            is Resource.Success -> {
                val searchResultsNetworkList = searchResults.data.geonames.map {
                    SearchResults(it.toponymName, it.adminName1, countryName = it.countryName)
                }
                return Resource.Success(data = searchResultsNetworkList)
            }
            is Resource.NoInternetConnection -> {
                return Resource.NoInternetConnection()

            }
            is Resource.Error -> {
                return Resource.Error(errorMessage = searchResults.errorMessage)
            }
        }
    }
}