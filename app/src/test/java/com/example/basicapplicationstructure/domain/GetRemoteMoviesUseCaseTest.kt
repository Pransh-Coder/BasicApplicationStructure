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

    //SUT
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
    fun `SUCCESS NETWORK CALL`() = runBlocking {

        val moviesNetworkResource = getRemoteMoviesUseCase.invoke()
        val actualNetworkList = moviesNetworkResource.data
        //get the list of the size for future
        val listSize = actualNetworkList?.size

        assertThat(moviesNetworkResource is Resource.Success).isTrue()

        assertThat(actualNetworkList?.get(0)?.title).isEqualTo("Avatar")
        assertThat(actualNetworkList?.get(1)?.title).isEqualTo("I Am Legend")
        assertThat(actualNetworkList?.get(2)?.title).isEqualTo("300")

        assertThat(listSize).isEqualTo(3)

        //checking whether the movies that are inserted in the local DB are same as per the network res
        localDataSource.getMoviesListFromDB().collectLatest {
            assertThat(it.data?.get(0)?.title).isEqualTo("Avatar")
            assertThat(it.data?.get(1)?.title).isEqualTo("I Am Legend")
            assertThat(it.data?.get(2)?.title).isEqualTo("300")

            // checking the size of the db response list with network response list
            assertThat(it.data?.size).isEqualTo(listSize)
        }
    }

    @Test
    fun `ERROR NETWORK CALL`() = runBlocking {

        fakeMoviesRepository.setUnsetNetworkError()

        val moviesNetworkResource = getRemoteMoviesUseCase.invoke()

        assertThat(moviesNetworkResource is Resource.Error).isTrue()
        assertThat(moviesNetworkResource.errorMessage).startsWith("Exception in")

        localDataSource.getMoviesListFromDB().collectLatest {
            //in the case of err the size of the list (res) would remain 0
            assertThat(it.data?.size).isEqualTo(0)
        }
    }

    @Test
    fun `No Internet Error`() = runBlocking {

        fakeMoviesRepository.setNoInternetErrForNetwork()

        val moviesNetworkResource = getRemoteMoviesUseCase.invoke()
        assertThat(moviesNetworkResource is Resource.NoInternetException).isTrue()
    }

    companion object{
        private const val TAG = "GetRemoteMoviesUseCaseT"
    }
}