package com.example.basicapplicationstructure.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.basicapplicationstructure.data.UserRepositoryInterface
import com.example.basicapplicationstructure.network.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(val repositoryInterface: UserRepositoryInterface): ViewModel() {

    private val _state = MutableStateFlow(UserState(isLoading = false))
    val state : StateFlow<UserState> = _state


    fun getUserBasedOnId(id: String){
        viewModelScope.launch{

            repositoryInterface.getUserDetails(id).collectLatest {
                when(it){
                    is NetworkResponse.Loading -> {
                        _state.value = state.value.copy(isLoading = true)
                    }
                    is NetworkResponse.Success -> {
                        _state.value = state.value.copy(isLoading = false, isPremiumUser = it.data.isPremiumUser)
                    }
                    is NetworkResponse.Error -> {
                        _state.value = state.value.copy(isLoading = false)
                    }
                }
            }
        }
    }
}