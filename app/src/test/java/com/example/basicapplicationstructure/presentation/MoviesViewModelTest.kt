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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MoviesViewModelTest {

    private lateinit var moviesViewModel: MoviesViewModel
    private lateinit var fakeMoviesRepository: FakeMoviesRepository
    private lateinit var fakeMoviesDaoImpl: FakeMoviesDaoImpl

    @ExperimentalCoroutinesApi
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule() // custom rule you define below

    @Before
    fun setUp(){
        fakeMoviesRepository = FakeMoviesRepository()
        //fakeMoviesDaoImpl = FakeMoviesDaoImpl()
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
    fun `CHECK THE MOVIES DATA AFTER DB SUCCEES CALL` () = runBlocking {

        assertInitialValues()
        moviesViewModel.getMoviesList()
        print("CHECK THE MOVIES DATA AFTER API SUCCEES CALL: ${moviesViewModel.state.value.moviesList}")
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

    companion object{
        private const val TAG = "MoviesViewModelTest"
    }
}