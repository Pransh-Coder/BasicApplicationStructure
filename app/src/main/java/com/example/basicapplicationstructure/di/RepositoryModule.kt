package com.example.basicapplicationstructure.di

import com.example.basicapplicationstructure.data.MoviesRepositoryImpl
import com.example.basicapplicationstructure.data.MoviesRepositoryInterface
import com.example.basicapplicationstructure.data.localDataSource.MoviesAppDatabase
import com.example.basicapplicationstructure.data.localDataSource.MoviesDao
import com.example.basicapplicationstructure.network.ApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun providesRepository(apiInterface: ApiInterface,dao: MoviesDao): MoviesRepositoryInterface {
        return MoviesRepositoryImpl(apiInterface = apiInterface, dao = dao)
    }

    @Provides
    @Singleton
    fun provideMovieDao(appDatabase: MoviesAppDatabase): MoviesDao {
        return appDatabase.moviesDao()
    }
}