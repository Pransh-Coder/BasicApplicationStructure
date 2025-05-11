package com.example.basicapplicationstructure.data.remoteDataSource

import com.example.basicapplicationstructure.data.AnimeDetailResponse
import com.example.basicapplicationstructure.data.AnimeResponse
import com.example.basicapplicationstructure.network.ApiInterface
import com.example.basicapplicationstructure.network.Resource
import java.lang.Exception
import java.net.UnknownHostException
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiInterface: ApiInterface) {

    suspend fun getAnimeListFromNetwork(): Resource<AnimeResponse> {
        return try {
            val response = apiInterface.getAnimeListFromServer()
            if (response.isSuccessful && response.body()!=null){
                Resource.Success(data = response.body()!!)
            }
            else{
                Resource.Error(errorMessage = response.errorBody().toString())
            }
        }catch (ex: UnknownHostException){
            Resource.NoInternetError()
        }
        catch (ex: Exception){
            Resource.Error(errorMessage = "Something went wrong! = $ex")
        }
    }

    suspend fun getAnimeDetailByIdNetwork(id: String): Resource<AnimeDetailResponse> {
        return try {
            val response = apiInterface.getAnimeDetailBasedOnId(id)
            if (response.isSuccessful && response.body()!=null){
                Resource.Success(data = response.body()!!)
            }
            else{
                Resource.Error(errorMessage = response.errorBody().toString())
            }
        }catch (ex: UnknownHostException){
            Resource.NoInternetError()
        }
        catch (ex: Exception){
            Resource.Error(errorMessage = "Something went wrong! = $ex")
        }
    }
}