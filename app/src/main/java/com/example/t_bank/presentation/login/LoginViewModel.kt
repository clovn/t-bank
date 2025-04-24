package com.example.t_bank.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.t_bank.domain.login.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class LoginState {
    data object Empty: LoginState()
    data object Loading: LoginState()
    data object Success: LoginState()
    data class Error(val message: String): LoginState()
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<LoginState>(LoginState.Empty)
    val uiState = _uiState.asStateFlow()

    fun login(login: String, password: String){
        viewModelScope.launch {
            try {
                _uiState.value = LoginState.Loading
                loginUseCase.invoke(login, password)
                _uiState.value = LoginState.Success
            } catch (e: Exception){
                _uiState.value = LoginState.Error(message = "Ошибка входа")
            }
        }
    }
}