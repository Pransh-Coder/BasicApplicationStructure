package com.example.basicapplicationstructure.network

sealed class Resource<T>(open val data:T ? = null, open val errorMessage: String? = null){

    class NoInternetConnection<T>() : Resource<T>()

    data class Success<T>(override val data: T) : Resource<T>(data)

    data class Error<T>(override val errorMessage: String) : Resource<T>(errorMessage = errorMessage)
}
