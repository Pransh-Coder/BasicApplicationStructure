package com.example.basicapplicationstructure.data.localDataSource

import androidx.room.Dao
import com.example.basicapplicationstructure.data.remoteDataSource.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    suspend fun getUserDataBasedOnId(id: String) : User?

    suspend fun addUserData(user: User)
}