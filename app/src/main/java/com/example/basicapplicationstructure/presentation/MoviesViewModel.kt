package com.example.basicapplicationstructure.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.basicapplicationstructure.domain.GetLocalMoviesUseCase
import com.example.basicapplicationstructure.domain.GetRemoteMoviesUseCase
import com.example.basicapplicationstructure.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getRemoteMoviesUseCase: GetRemoteMoviesUseCase,
    private val getLocalMoviesUseCase: GetLocalMoviesUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(MoviesState(isLoading = false))
    val state : StateFlow<MoviesState> = _state

    //use case of channel & why?
    private val _errors = Channel<String>()
    val error = _errors.receiveAsFlow()

    /*init {
        getMoviesList()
    }*/

    fun getMoviesList(){
        println("inside getMoviesList")
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }
            getLocalMoviesUseCase.invoke().collectLatest {
                //Log.e(TAG, "getMoviesList: it = $it")
                when(it){
                    is Resource.Success -> {
                        if (it.data.isNotEmpty()){
                            //Log.e(TAG, "getMoviesList: data is not empty...", )
                            println("getMoviesList: data is not empty... ${it.data}")
                            _state.value = state.value.copy(isLoading = false, moviesList = it.data)
                        }
                        else{
                            //Log.e(TAG, "getMoviesList: data is empty fetching from network", )
                            println("getMoviesList: data is empty fetching from network")
                            fetchDataFromServer()
                        }
                    }
                    is Resource.NoInternetException -> {
                        //Log.e(TAG, "getMoviesList: err = ${it.errorMessage}", )
                        println("Internet err from DB")
                        _state.update {
                            it.copy(isLoading = false)
                        }

                        _errors.send("No Internet Connection!")
                    }
                    is Resource.Error -> {
                        _state.update {
                            it.copy(isLoading = false)
                        }
                        _errors.send(it.errorMessage)
                    }
                }
            }
        }
    }

    private fun fetchDataFromServer(){
        viewModelScope.launch {
            val networkResponse = getRemoteMoviesUseCase.invoke()

            Log.e(TAG, "fetchDataFromServer: networkResponse = $networkResponse")

            when(networkResponse){
                is Resource.Success -> {
                    _state.value = state.value.copy(isLoading = false, moviesList = networkResponse.data)
                }
                is Resource.NoInternetException -> {
                    Log.e(TAG, "getMoviesList: err = ${networkResponse.errorMessage}", )
                    println("getMoviesList: NoInternet Err from SERVER ")
                    _state.update {
                        it.copy(isLoading = false)
                    }
                    _errors.send("No Internet Connection From Network!")
                }
                is Resource.Error -> {
                    println("getMoviesList: Err from Network errorMessage = ${networkResponse.errorMessage}")
                    _state.update {
                        it.copy(isLoading = false)
                    }
                    _errors.send(networkResponse.errorMessage)
                }
            }
        }
    }

    companion object{
        private const val TAG = "MoviesViewModel"
    }
}