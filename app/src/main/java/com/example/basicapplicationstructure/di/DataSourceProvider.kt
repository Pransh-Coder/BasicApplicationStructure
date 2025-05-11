package com.example.basicapplicationstructure.di

import com.example.basicapplicationstructure.data.SearchRepositoryImpl
import com.example.basicapplicationstructure.data.SearchRepositoryInterface
import com.example.basicapplicationstructure.data.remoteDataSource.RemoteDataSource
import com.example.basicapplicationstructure.network.ApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceProvider {

    @Provides
    @Singleton
    fun providesRemoteDataSource(apiInterface: ApiInterface): RemoteDataSource {
        return RemoteDataSource(apiInterface)
    }

    @Provides
    @Singleton
    fun providesRepository(remoteDataSource: RemoteDataSource): SearchRepositoryInterface {
        return SearchRepositoryImpl(remoteDataSource)
    }
}