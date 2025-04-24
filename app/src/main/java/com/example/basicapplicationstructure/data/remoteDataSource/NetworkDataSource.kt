package com.example.basicapplicationstructure.data.remoteDataSource

import com.example.basicapplicationstructure.network.ApiInterface
import com.example.basicapplicationstructure.network.Resource
import java.net.UnknownHostException
import javax.inject.Inject

class NetworkDataSource @Inject constructor(val apiInterface: ApiInterface) {
    //we are not getting the flow of data from the backend server it could be either success or failure,
    //so no need to return flow from here
    suspend fun getMoviesListFromNetwork(): Resource<List<MoviesResponse>> {
        return try {
            val networkRes = apiInterface.getMoviesListFromServer()

            if (networkRes.body() != null && networkRes.isSuccessful){
                Resource.Success(data = networkRes.body()!!)
            }
            else{
                Resource.Error(errorMessage = networkRes.errorBody().toString())
            }
        }
        catch (ex: UnknownHostException){
            Resource.NoInternetException()
        }
        catch (ex: Exception){
            Resource.Error(errorMessage = ex.message.toString())
        }
    }
}