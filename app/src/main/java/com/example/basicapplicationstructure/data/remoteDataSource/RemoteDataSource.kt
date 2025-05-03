package com.example.basicapplicationstructure.data.remoteDataSource

import com.example.basicapplicationstructure.data.SearchResponse
import com.example.basicapplicationstructure.network.ApiInterface
import com.example.basicapplicationstructure.network.Resource
import java.net.UnknownHostException
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiInterface: ApiInterface) {

    suspend fun getSearchResultsBasedOnNames(nameStartsWith: String, maxRows: Int, userName: String): Resource<SearchResponse>{

        return try {
            val response = apiInterface.getSearchResultsBasedOnNames(nameStartsWith, maxRows, userName)

            if (response.isSuccessful && response.body()!= null){
                return Resource.Success(response.body()!!)
            }else{
                return Resource.Error(response.errorBody().toString())
            }
        }catch (ex: UnknownHostException){
            Resource.NoInternetConnection()
        }
        catch (ex: Exception){
            return Resource.Error("Something went wrong! $ex")
        }
    }
}