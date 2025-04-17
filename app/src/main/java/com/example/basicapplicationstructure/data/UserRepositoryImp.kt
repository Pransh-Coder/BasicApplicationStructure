package com.example.basicapplicationstructure.data

import com.example.basicapplicationstructure.data.localDataSource.UserDao
import com.example.basicapplicationstructure.data.remoteDataSource.User
import com.example.basicapplicationstructure.network.ApiInterface
import com.example.basicapplicationstructure.network.NetworkResponse
import com.example.basicapplicationstructure.network.invokeOnStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImp @Inject constructor(val apiInterface: ApiInterface, val userDao: UserDao): UserRepositoryInterface {

    override fun getUserDetails(id: String): Flow<NetworkResponse<User>> {

        return flow {
            emit(NetworkResponse.Loading())
            try {
                val response = userDao.getUserDataBasedOnId(id)
                if (response == null){
                    val responseFromNetwork = apiInterface.getUserDataById(id = id)

                    responseFromNetwork.code().invokeOnStatus(
                        onSuccess = {
                            responseFromNetwork.body()?.let {
                                emit(NetworkResponse.Success(data = it))

                                userDao.addUserData(user = it)
                            }
                        },
                        onError = {
                            emit(NetworkResponse.Error(responseFromNetwork.errorBody().toString()))
                        }
                    )
                }
                else{
                    emit(NetworkResponse.Success(response))
                }
            }catch (ex: Exception){
                emit(NetworkResponse.Error("data not found or someth $ex"))
            }
        }
    }
}