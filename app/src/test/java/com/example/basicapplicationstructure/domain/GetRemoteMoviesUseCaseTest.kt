package com.example.basicapplicationstructure.domain

import android.util.Log
import com.example.basicapplicationstructure.data.localDataSource.LocalDataSource
import com.example.basicapplicationstructure.data.repository.FakeMoviesDaoImpl
import com.example.basicapplicationstructure.data.repository.FakeMoviesRepository
import com.example.basicapplicationstructure.network.Resource
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetRemoteMoviesUseCaseTest {

    private lateinit var getRemoteMoviesUseCase: GetRemoteMoviesUseCase
    private lateinit var fakeMoviesRepository: FakeMoviesRepository
    private lateinit var localDataSource: LocalDataSource
    private lateinit var daoImpl: FakeMoviesDaoImpl

    @Before
    fun setUp(){
        fakeMoviesRepository = FakeMoviesRepository()
        daoImpl = FakeMoviesDaoImpl()
        localDataSource = LocalDataSource(daoImpl)
        getRemoteMoviesUseCase = GetRemoteMoviesUseCase(
            moviesRepositoryInterface = fakeMoviesRepository,
            localDataSource = localDataSource
        )
    }

    @Test
    fun `SUCCEES NETWORK CALL`() = runBlocking {

        val moviesNetworkResource = getRemoteMoviesUseCase.invoke()
        val actualNetworkList = moviesNetworkResource.data
        val listSize = actualNetworkList?.size

        assertThat(moviesNetworkResource is Resource.Success).isTrue()

        assertThat(actualNetworkList?.get(0)?.title).isEqualTo("Avatar")
        assertThat(actualNetworkList?.get(1)?.title).isEqualTo("I Am Legend")
        assertThat(actualNetworkList?.get(2)?.title).isEqualTo("300")
        assertThat(listSize == 3).isTrue()

        localDataSource.getMoviesListFromDB().collectLatest {
            assertThat(it.data?.get(0)?.title).isEqualTo("Avatar")
            assertThat(it.data?.get(1)?.title).isEqualTo("I Am Legend")
            assertThat(it.data?.get(2)?.title).isEqualTo("300")

            assertThat(it.data?.size == listSize).isTrue()
        }
    }

    @Test
    fun `ERROR NETWORK CALL`() = runBlocking {

        fakeMoviesRepository.setUnsetError()

        val moviesNetworkResource = getRemoteMoviesUseCase.invoke()

        assertThat(moviesNetworkResource is Resource.Error).isTrue()
        assertThat(moviesNetworkResource.errorMessage).startsWith("")

        localDataSource.getMoviesListFromDB().collectLatest {
            assertThat(it.data?.size == 0).isTrue()
        }
    }

    @Test
    fun `No Internet Error`() = runBlocking {

        fakeMoviesRepository.setNoInternetErr()
        val moviesNetworkResource = getRemoteMoviesUseCase.invoke()
        assertThat(moviesNetworkResource is Resource.NoInternetException).isTrue()
    }

    companion object{
        private const val TAG = "GetRemoteMoviesUseCaseT"
    }
}