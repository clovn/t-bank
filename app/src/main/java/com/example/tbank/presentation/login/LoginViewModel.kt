package com.example.tbank.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbank.data.model.ResultWrapper
import com.example.tbank.domain.login.IsLoggedUseCase
import com.example.tbank.domain.login.LoginUseCase
import com.example.tbank.domain.model.User
import com.example.tbank.presentation.normalizePhoneNumber
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class LoginState {
    data object Empty: LoginState()
    data object Logged: LoginState()
    data object Loading: LoginState()
    data object Success: LoginState()
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val isLoggedUseCase: IsLoggedUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<LoginState>(LoginState.Empty)
    val uiState: StateFlow<LoginState> = _uiState

    private val _errorFlow = MutableSharedFlow<String>()
    val errorFlow = _errorFlow

    fun login(number: String, password: String){
        viewModelScope.launch {
            _uiState.update { LoginState.Loading }

            when(val result = loginUseCase.invoke(normalizePhoneNumber(number), password)) {
                is ResultWrapper.Success<User> -> _uiState.update { LoginState.Success }
                is ResultWrapper.Error -> {
                    _errorFlow.emit(result.message ?: "Неизвестная ошибка")
                    _uiState.update { LoginState.Empty }
                }
                is ResultWrapper.HttpError -> {
                    _errorFlow.emit("${result.message} (${result.code})")
                    _uiState.update { LoginState.Empty }
                }
            }
        }
    }

    fun checkLogin() {
        viewModelScope.launch {
            if(isLoggedUseCase.invoke()){
                _uiState.update {
                    LoginState.Logged
                }
            }
        }
    }
}
