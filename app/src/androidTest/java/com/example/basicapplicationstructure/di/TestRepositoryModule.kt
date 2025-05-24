package com.example.basicapplicationstructure.di

import com.example.basicapplicationstructure.data.MoviesRepositoryImpl
import com.example.basicapplicationstructure.data.MoviesRepositoryInterface
import com.example.basicapplicationstructure.data.localDataSource.LocalDataSource
import com.example.basicapplicationstructure.data.localDataSource.MoviesAppDatabase
import com.example.basicapplicationstructure.data.localDataSource.MoviesDao
import com.example.basicapplicationstructure.data.remoteDataSource.NetworkDataSource
import com.example.basicapplicationstructure.network.ApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TestRepositoryModule {

    @Provides
    @Singleton
    fun providesNetworkDataSource(apiInterface: ApiInterface): NetworkDataSource {
        return NetworkDataSource(apiInterface = apiInterface)
    }

    @Provides
    @Singleton
    fun providesLocalDataSource(dao: MoviesDao): LocalDataSource {
        return LocalDataSource(dao = dao)
    }


    @Provides
    @Singleton
    fun providesRepository(networkDataSource: NetworkDataSource,localDataSource: LocalDataSource): MoviesRepositoryInterface {
        return MoviesRepositoryImpl(networkDataSource = networkDataSource, localDataSource = localDataSource)
    }

    @Provides
    @Singleton
    fun provideMovieDao(appDatabase: MoviesAppDatabase): MoviesDao {
        return appDatabase.moviesDao()
    }
}