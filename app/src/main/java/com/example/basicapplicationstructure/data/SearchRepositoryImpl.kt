package com.example.basicapplicationstructure.data

import com.example.basicapplicationstructure.data.remoteDataSource.RemoteDataSource
import com.example.basicapplicationstructure.network.Resource
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource) : SearchRepositoryInterface {

    override suspend fun getSearchResultsBasedOnNames(
        nameStartsWith: String,
        maxRows: Int,
        userName: String,
    ): Resource<SearchResponse> {

        return remoteDataSource.getSearchResultsBasedOnNames(nameStartsWith, maxRows, userName)
    }
}