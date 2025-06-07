package com.example.tbank.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbank.data.model.ResultWrapper
import com.example.tbank.domain.main.GetUserInfoUseCase
import com.example.tbank.domain.main.LogoutUseCase
import com.example.tbank.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ProfileState {
    data object Loading: ProfileState()
    data class Success(val user: User): ProfileState()
    data class Error(val message: String): ProfileState()
    data object Logout: ProfileState()
}

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<ProfileState>(ProfileState.Loading)
    val state: StateFlow<ProfileState> = _state

    fun fetchUser(){
        _state.update {
            ProfileState.Loading
        }
        viewModelScope.launch {
            when(val result = getUserInfoUseCase.invoke()){
                is ResultWrapper.Success -> {
                    _state.update {
                        ProfileState.Success(result.value)
                    }
                }

                is ResultWrapper.Error -> {
                    _state.update {
                        ProfileState.Error(message = result.message ?: "Неизвестная ошибка")
                    }
                }

                is ResultWrapper.HttpError -> {
                    _state.update {
                        ProfileState.Error(message = "${result.message} - ${result.code}")
                    }
                }
            }
        }
    }

    fun logout(){
        viewModelScope.launch {
            logoutUseCase.invoke()
            _state.update {
                ProfileState.Logout
            }
        }
    }
}