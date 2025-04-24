package com.example.t_bank.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.t_bank.domain.login.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
            runCatching {
                _uiState.value = LoginState.Loading
                loginUseCase.invoke(login, password)
            }.onSuccess {
                _uiState.value = LoginState.Success
            }.onFailure {
                _errorFlow.emit(it.message ?: "Ошибка")
                _uiState.value = LoginState.Empty
            }
        }
    }
}