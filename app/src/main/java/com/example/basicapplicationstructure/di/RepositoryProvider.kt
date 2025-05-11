package com.example.basicapplicationstructure.di

import com.example.basicapplicationstructure.data.AnimeRepositoryImpl
import com.example.basicapplicationstructure.data.AnimeRepositoryInterface
import com.example.basicapplicationstructure.data.remoteDataSource.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryProvider {

    @Provides
    @Singleton
    fun providesRepository(remoteDataSource: RemoteDataSource): AnimeRepositoryInterface {
        return AnimeRepositoryImpl(remoteDataSource = remoteDataSource)
    }

}