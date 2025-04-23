package com.example.basicapplicationstructure.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.basicapplicationstructure.domain.GetLocalMoviesUseCase
import com.example.basicapplicationstructure.domain.MoviesUseCase
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
    private val moviesUseCase: MoviesUseCase,
    val getLocalMoviesUseCase: GetLocalMoviesUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(MoviesState(isLoading = false))
    val state : StateFlow<MoviesState> = _state

    //use case of channel & why?
    private val _errors = Channel<String>()
    val error = _errors.receiveAsFlow()

    init {
        getMoviesList()
    }

    fun getMoviesList(){
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }
            getLocalMoviesUseCase.invoke().collectLatest {
                Log.e(TAG, "getMoviesList: it = $it")
                when(it){
                    is Resource.Error -> {
                        _state.update {
                            it.copy(isLoading = false)
                        }
                        _errors.send(it.errorMessage)
                    }
                    is Resource.NoInternetException -> {
                        _errors.send("No Internet Connection!")
                    }
                    is Resource.Success -> {
                        if (it.data.isNotEmpty()){
                            Log.e(TAG, "getMoviesList: data is not empty...", )
                            _state.value = state.value.copy(isLoading = false, moviesList = it.data)
                        }
                        else{
                            Log.e(TAG, "getMoviesList: data is empty fetching from network", )
                            fetchDataFromServer()
                        }
                    }
                }
            }
        }
    }

    private fun fetchDataFromServer(){
        viewModelScope.launch {
            val networkResponse = moviesUseCase.invoke()
            Log.e(TAG, "fetchDataFromServer: networkResponse = $networkResponse")
            when(networkResponse){
                is Resource.Error -> {
                    _state.update {
                        it.copy(isLoading = false)
                    }
                    _errors.send(networkResponse.errorMessage)
                }
                is Resource.NoInternetException -> {
                    _errors.send("No Internet Connection!")
                }
                is Resource.Success -> {
                    _state.value = state.value.copy(isLoading = false, moviesList = networkResponse.data)
                }
            }
        }
    }

    companion object{
        private const val TAG = "MoviesViewModel"
    }
}