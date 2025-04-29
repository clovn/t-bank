package com.example.tbank.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbank.data.model.ResultWrapper
import com.example.tbank.domain.model.User
import com.example.tbank.domain.register.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class RegisterState {
    data object Idle: RegisterState()
    data object Loading: RegisterState()
    data object Success: RegisterState()
}

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val uiState: StateFlow<RegisterState> = _uiState

    private val _errorFlow = MutableSharedFlow<String>()
    val errorFlow: SharedFlow<String> = _errorFlow

    fun register(username: String, firstName: String, lastName: String, number: String, password: String, confirmPassword: String){
        viewModelScope.launch {
            _uiState.update { RegisterState.Loading }

            val result = registerUseCase.invoke(username, firstName, lastName, number, password)
            when(result) {
                is ResultWrapper.Success<User> -> _uiState.update { RegisterState.Success }
                is ResultWrapper.Error -> {
                    _errorFlow.emit(result.message ?: "Неизвестная ошибка")
                    _uiState.update { RegisterState.Idle }
                }
            }
        }
    }
}