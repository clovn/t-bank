package com.example.tbank.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbank.data.model.ResultWrapper
import com.example.tbank.domain.login.LoginUseCase
import com.example.tbank.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class LoginState {
    data object Empty: LoginState()
    data object Loading: LoginState()
    data object Success: LoginState()
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<LoginState>(LoginState.Empty)
    val uiState: StateFlow<LoginState> = _uiState

    private val _errorFlow = MutableSharedFlow<String>()
    val errorFlow = _errorFlow

    fun login(login: String, password: String){
        viewModelScope.launch {
            _uiState.update { LoginState.Loading }

            when(val result = loginUseCase.invoke(login, password)) {
                is ResultWrapper.Success<User> -> _uiState.update { LoginState.Success }
                is ResultWrapper.Error -> {
                    _errorFlow.emit(result.message ?: "Неизвестная ошибка")
                    _uiState.update { LoginState.Empty }
                }
            }
        }
    }
}
