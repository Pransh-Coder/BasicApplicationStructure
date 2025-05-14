package com.example.basicapplicationstructure.domain

import com.example.basicapplicationstructure.data.repository.FakeMoviesRepository
import com.example.basicapplicationstructure.network.Resource
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetLocalMoviesUseCaseTest {
    //System Under Test (SUT)
    private lateinit var getLocalMoviesUseCase: GetLocalMoviesUseCase

    private lateinit var fakeMoviesRepository: FakeMoviesRepository

    //this would run this setUp func before every single testcase & will be used to setup things
    // that we need for every usecase
    @Before
    fun setUp(){
        fakeMoviesRepository = FakeMoviesRepository()
        //creating an obj for SUT
        getLocalMoviesUseCase = GetLocalMoviesUseCase(moviesRepositoryInterface = fakeMoviesRepository)
    }

    // emailValidator_CorrectEmailSimple_ReturnsTrue()
    @Test
    fun `SUCCESS DB CALL`() = runBlocking {

        getLocalMoviesUseCase.invoke().collectLatest {

            assertTrue(it is Resource.Success)
            assertThat(it.data?.get(0)?.title).isEqualTo("Avatar")
            assertThat(it.data?.get(0)?.year).isEqualTo("2009")
            //it.data?.get(0)?.title == "Avatar" && it.data?.get(0)?.year == "2009
        }
    }

    @Test
    fun `ERROR DB CALL`() = runBlocking {

        fakeMoviesRepository.setUnsetError()

        getLocalMoviesUseCase.invoke().collectLatest {
            assertTrue(it is Resource.Error)
            assertThat(it is Resource.Success).isFalse()
            assertThat(it.errorMessage).startsWith("Excep in DB")
        }

        //fakeMoviesRepository.setUnsetError()
    }

    @Test
    fun `INTERNET ERROR DB `() = runBlocking {

        fakeMoviesRepository.setNoInternetErr()

        getLocalMoviesUseCase.invoke().collectLatest {
            assertThat(it is Resource.NoInternetException).isTrue()
        }

        fakeMoviesRepository.setNoInternetErr()

    }
}