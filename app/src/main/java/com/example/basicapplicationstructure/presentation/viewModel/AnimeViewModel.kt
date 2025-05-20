package com.example.basicapplicationstructure.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.basicapplicationstructure.domain.GetAnimeDetailUseCase
import com.example.basicapplicationstructure.domain.GetAnimeListDataUseCase
import com.example.basicapplicationstructure.network.Resource
import com.example.basicapplicationstructure.presentation.states.AnimeDetailState
import com.example.basicapplicationstructure.presentation.states.AnimeListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeViewModel @Inject constructor(
    private val animeListUseCase: GetAnimeListDataUseCase,
    private val animeDetailUseCase: GetAnimeDetailUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(AnimeListState(isLoading = false))
    val state: StateFlow<AnimeListState> = _state

    private val _detailState = MutableStateFlow(AnimeDetailState(isLoading = false))
    val detailState: StateFlow<AnimeDetailState> = _detailState

    private val _errors = Channel<String>()
    val errors = _errors.receiveAsFlow()

    private val _screenOneTimeEvents = Channel<OneTimeEvents>()
    val screenOneTimeEvents = _screenOneTimeEvents.receiveAsFlow()

    sealed class OneTimeEvents {
        object NavigateToNextScreen: OneTimeEvents()
    }

    init {
        //todo remove it should not be called in constructor, not a good approach, it should be directly called from view
        getAnimeListFromNetwork()
    }

    fun getAnimeListFromNetwork() {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }
            val networkResponse = animeListUseCase.invoke()
            when (networkResponse) {
                is Resource.Success -> {
                    Log.e(TAG, "getAnimeListFromNetwork: data = ${networkResponse.data}")
                    _state.update {
                        it.copy(isLoading = false, animeList = networkResponse.data)
                    }
                }

                is Resource.NoInternetError -> {
                    _state.update {
                        it.copy(isLoading = false)
                    }
                    _errors.send("No Internet Connection Available!")
                }

                is Resource.Error -> {
                    Log.e(TAG, "getAnimeListFromNetwork: err = ${networkResponse.errorMessage}")
                    _state.update {
                        it.copy(isLoading = false)
                    }
                    _errors.send(networkResponse.errorMessage)
                }
            }
        }
    }

    fun onAnimeClicked(id: String){
        viewModelScope.launch {
            getAnimeDetailsById(id)
            _screenOneTimeEvents.send(OneTimeEvents.NavigateToNextScreen)
        }
    }

    private fun getAnimeDetailsById(id: String) {
        viewModelScope.launch {
            _detailState.update {
                it.copy(isLoading = true)
            }

            val networkDetailsResponse = animeDetailUseCase.invoke(id)
            when (networkDetailsResponse) {
                is Resource.Success -> {
                    Log.e(TAG, "getAnimeDetailsById: data by id = ${networkDetailsResponse.data}")
                    _detailState.update {
                        it.copy(isLoading = false, animeDetailsData = networkDetailsResponse.data)
                    }
                }

                is Resource.NoInternetError -> {
                    _detailState.update {
                        it.copy(isLoading = false)
                    }
                    _errors.send("No Internet Connection Available!")
                }

                is Resource.Error -> {
                    Log.e(TAG, "getAnimeDetailsById: err = ${networkDetailsResponse.errorMessage}")
                    _detailState.update {
                        it.copy(isLoading = false)
                    }
                    _errors.send(networkDetailsResponse.errorMessage)
                }
            }
        }
    }

    companion object {
        private const val TAG = "AnimeListViewModel"
    }
}