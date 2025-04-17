package com.example.basicapplicationstructure.data

import com.example.basicapplicationstructure.data.remoteDataSource.User
import com.example.basicapplicationstructure.network.NetworkResponse
import kotlinx.coroutines.flow.Flow

interface UserRepositoryInterface {

    fun getUserDetails(id: String) : Flow<NetworkResponse<User>>
}