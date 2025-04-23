package com.example.basicapplicationstructure.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.basicapplicationstructure.domain.MoviesUseCase
import com.example.basicapplicationstructure.network.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val moviesUseCase: MoviesUseCase): ViewModel() {

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
            moviesUseCase.invoke().collectLatest {
                when(it){
                    is NetworkResponse.Loading-> {
                        _state.value = state.value.copy(isLoading = true)
                    }

                    is NetworkResponse.Success -> {
                        delay(5000)
                        _state.value = state.value.copy(isLoading = false, moviesList = it.data)
                    }

                    is NetworkResponse.Error -> {
                        Log.e(TAG, "getMoviesList: error = ${it.errorMessage}")
                        _state.value = state.value.copy(isLoading = false)

                        _errors.send(it.errorMessage)
                    }
                }
            }
        }
    }

    companion object{
        private const val TAG = "MoviesViewModel"
    }
}