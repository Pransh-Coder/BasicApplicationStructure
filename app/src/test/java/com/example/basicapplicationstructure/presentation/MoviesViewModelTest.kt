package com.example.basicapplicationstructure.presentation

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.basicapplicationstructure.MainDispatcherRule
import com.example.basicapplicationstructure.data.localDataSource.LocalDataSource
import com.example.basicapplicationstructure.data.repository.FakeMoviesDaoImpl
import com.example.basicapplicationstructure.data.repository.FakeMoviesRepository
import com.example.basicapplicationstructure.domain.GetLocalMoviesUseCase
import com.example.basicapplicationstructure.domain.GetRemoteMoviesUseCase
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MoviesViewModelTest {

    //SUT
    private lateinit var moviesViewModel: MoviesViewModel

    private lateinit var fakeMoviesRepository: FakeMoviesRepository
    private lateinit var fakeMoviesDaoImpl: FakeMoviesDaoImpl

    //This code is required to test viewModelScope.launch coroutine
    @ExperimentalCoroutinesApi
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule() // custom rule you define below

    @Before
    fun setUp(){
        //This block is needed to make log statements to start working in compose
        mockkStatic(Log::class)
        // Arrange
        every { Log.e(any(), any()) } returns 0


        fakeMoviesRepository = FakeMoviesRepository()

        moviesViewModel = MoviesViewModel(
            getRemoteMoviesUseCase = GetRemoteMoviesUseCase(
                moviesRepositoryInterface = fakeMoviesRepository,
                localDataSource = LocalDataSource(dao = FakeMoviesDaoImpl())
            ),
            getLocalMoviesUseCase = GetLocalMoviesUseCase(fakeMoviesRepository)
        )
    }

    fun assertInitialValues() = runBlocking {
        assertThat(moviesViewModel.state.value.isLoading == false).isTrue()
        assertThat(moviesViewModel.state.value.moviesList?.isEmpty()).isTrue()
    }

    @Test
    fun `MOVIES DATA AFTER DB SUCCESS CALL` () = runBlocking {

        assertInitialValues()
        moviesViewModel.getMoviesList()
        print("CHECK THE MOVIES DATA AFTER DB SUCCESS CALL: ${moviesViewModel.state.value.moviesList}")
        assertThat(moviesViewModel.state.value.moviesList?.isNotEmpty()).isTrue()

       /*moviesViewModel.state.value {
           assertThat(it.moviesList.isNullOrEmpty().not()).isEqualTo(true)
       }*/
    }

    /*@After
    fun cleanUp(){
        fakeMoviesDaoImpl.clearDataBase()
    }*/

    @Test
    fun `CHECK THE DB ERROR`()  = runBlocking {

        assertInitialValues()
       /* moviesViewModel.error.collectLatest {
            assertThat(it == "Exception in API call").isTrue()
        }*/
        //assertThat(moviesViewModel.state.value.moviesList?.isEmpty()).isTrue()
       fakeMoviesRepository.setUnsetError()

       moviesViewModel.getMoviesList()
       moviesViewModel.error.take(1).collect {
            assertThat(it).isEqualTo("Excep in DB")
       }
        //assertThat(fakeMoviesDaoImpl.getAllMoviesList().size).isEqualTo(0)

        //assertThat(result.moviesList?.size).isEqualTo(3)
    }

    @Test
    fun `CHECK NO INTERNET DB ERROR`() = runBlocking {

        assertInitialValues()

        fakeMoviesRepository.setNoInternetErr()

        moviesViewModel.getMoviesList()

        moviesViewModel.error.take(1).collect {
            assertThat(it).isEqualTo("No Internet Connection!")
        }
        assertThat(moviesViewModel.state.value.moviesList?.isEmpty()).isTrue()
    }

    @Test
    fun `MOVIES DATA AFTER NETWORK SUCCESS CALL` () = runBlocking {

        assertInitialValues()
        fakeMoviesRepository.isAppLaunchedInitially()
        moviesViewModel.getMoviesList()
        print("CHECK THE MOVIES DATA AFTER API SUCCESS CALL: ${moviesViewModel.state.value.moviesList}")
        assertThat(moviesViewModel.state.value.moviesList?.size).isNotEqualTo(0)

        /*moviesViewModel.state.value {
            assertThat(it.moviesList.isNullOrEmpty().not()).isEqualTo(true)
        }*/
    }

    @Test
    fun `CHECK NETWORK ERROR`() = runBlocking {

        assertInitialValues()
        fakeMoviesRepository.isAppLaunchedInitially()
        fakeMoviesRepository.setUnsetNetworkError()
        moviesViewModel.getMoviesList()
        moviesViewModel.error.take(1).collect {
            assertThat(it).isEqualTo("Exception in API call")
        }
        assertThat(moviesViewModel.state.value.moviesList?.size).isEqualTo(0)
    }

    @Test
    fun `CHECK NO INTERNET NETWORK ERROR`() = runBlocking {

        assertInitialValues()
        fakeMoviesRepository.isAppLaunchedInitially()
        fakeMoviesRepository.setNoInternetErrForNetwork()
        moviesViewModel.getMoviesList()
        moviesViewModel.error.take(1).collect {
            assertThat(it).isEqualTo("No Internet Connection From Network!")
        }
        assertThat(moviesViewModel.state.value.moviesList?.size).isEqualTo(0)
    }

    companion object{
        private const val TAG = "MoviesViewModelTest"
    }
}