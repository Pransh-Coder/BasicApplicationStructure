package com.example.basicapplicationstructure.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.basicapplicationstructure.domain.GetSearchUseCase
import com.example.basicapplicationstructure.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchResultsViewModel @Inject constructor(private val useCase: GetSearchUseCase): ViewModel() {

    private val _state = MutableStateFlow(SearchState(isLoading = false))
    val state : StateFlow<SearchState> = _state

    private val _errors = Channel<String>()
    val errors = _errors.receiveAsFlow()

    // TODO: caching the data from the network
    fun getSearchResults(nameStartsWith: String, maxRows: Int, userName: String){
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }
            val searchResultsResponse = useCase.invoke(maxRows = maxRows, nameStartsWith = nameStartsWith, userName = userName)
            when(searchResultsResponse){
                is Resource.Success -> {
                    Log.e(TAG, "getSearchResults: res = ${searchResultsResponse.data}")
                    _state.update {
                        it.copy(isLoading = false, searchResults = searchResultsResponse.data)
                    }
                }
                is Resource.NoInternetConnection -> {
                     Log.e(TAG, "getSearchResults: err = ${searchResultsResponse.errorMessage}")
                    _state.update {
                        it.copy(isLoading = false)
                    }
                    _errors.send("No Internet Connection!")
                }
                is Resource.Error-> {
                    Log.e(TAG, "getSearchResults: err = ${searchResultsResponse.errorMessage}",)
                    _state.update {
                        it.copy(isLoading = false)
                    }
                    _errors.send(searchResultsResponse.errorMessage)
                }
            }
        }
    }
    companion object{
        private const val TAG = "SearchResultsViewModel"
    }
}