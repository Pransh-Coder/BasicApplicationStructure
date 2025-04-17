package com.example.basicapplicationstructure.data.remoteDataSource

data class User(
    val id:Int,
    val name: String,
    val isPremiumUser: Boolean = false
)