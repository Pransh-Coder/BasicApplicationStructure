package com.example.basicapplicationstructure.data

import com.example.basicapplicationstructure.network.Resource

interface SearchRepositoryInterface {

    suspend fun getSearchResultsBasedOnNames(
        nameStartsWith: String,
        maxRows: Int,
        userName: String,
    ): Resource<SearchResponse>
}