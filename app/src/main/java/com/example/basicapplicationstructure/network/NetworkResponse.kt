package com.example.basicapplicationstructure.network

sealed class NetworkResponse<T>(open val data:T ? = null, open val errorMessage: String? = null){

    class Loading<T>() : NetworkResponse<T>()

    data class Success<T>(override val data: T) : NetworkResponse<T>(data)

    data class Error<T>(override val errorMessage: String) : NetworkResponse<T>(errorMessage = errorMessage)
}
