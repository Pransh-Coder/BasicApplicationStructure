package com.example.basicapplicationstructure.data.remoteDataSource

import com.example.basicapplicationstructure.network.ApiInterface
import com.example.basicapplicationstructure.network.Resource
import org.json.JSONException
import java.net.UnknownHostException
import javax.inject.Inject

class NetworkDataSource @Inject constructor(val apiInterface: ApiInterface) {

    suspend fun getMoviesListFromNetwork(): Resource<List<MoviesResponse>> {
        return try {
            val networkRes = apiInterface.getMoviesListFromServer()

            if (networkRes.body() != null && networkRes.isSuccessful){
                Resource.Success(data = networkRes.body()!!)
            }
            else{
                Resource.Error(errorMessage = networkRes.errorBody().toString())
            }
        }catch (ex: JSONException){
            Resource.Error(errorMessage = ex.message.toString())
        }
        catch (ex: UnknownHostException){
            Resource.NoInternetException()
        }
        catch (ex: Exception){
            Resource.Error(errorMessage = ex.message.toString())
        }
    }
}