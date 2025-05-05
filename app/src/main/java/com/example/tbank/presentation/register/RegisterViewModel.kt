package com.example.tbank.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbank.data.model.ResultWrapper
import com.example.tbank.domain.model.User
import com.example.tbank.domain.register.RegisterUseCase
import com.example.tbank.domain.validation.ValidationManager
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

data class FieldState(
    val value: String = "",
    val isValid: Boolean = true
)

data class RegisterFormState(
    val loginState: FieldState = FieldState(),
    val firstNameState: FieldState = FieldState(),
    val lastNameState: FieldState = FieldState(),
    val passwordState: FieldState = FieldState(),
    val numberState: FieldState = FieldState(),
    val confirmPasswordState: FieldState = FieldState(),
    val isRegisterBtnActive: Boolean = false
)

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val validationManager: ValidationManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val uiState: StateFlow<RegisterState> = _uiState

    private val _formState = MutableStateFlow(RegisterFormState())
    val formState: StateFlow<RegisterFormState> = _formState

    private val _errorFlow = MutableSharedFlow<String>()
    val errorFlow: SharedFlow<String> = _errorFlow

    fun register(){
        viewModelScope.launch {
            _uiState.update { RegisterState.Loading }

            val currentState = _formState.value
            val result = registerUseCase.invoke(
                currentState.loginState.value,
                currentState.firstNameState.value,
                currentState.lastNameState.value,
                currentState.numberState.value,
                currentState.passwordState.value
            )

            when(result) {
                is ResultWrapper.Success<User> -> _uiState.update { RegisterState.Success }
                is ResultWrapper.Error -> {
                    _errorFlow.emit(result.message ?: "Неизвестная ошибка")
                    _uiState.update { RegisterState.Idle }
                }
                is ResultWrapper.HttpError -> {
                    _errorFlow.emit("${result.message} (${result.code})")
                    _uiState.update { RegisterState.Idle }
                }
            }
        }
    }

    fun onLoginChanged(login: String) {
        updateFieldAndValidate {
            copy(loginState = FieldState(login, validationManager.isValidLogin(login)))
        }
    }

    fun onFirstNameChanged(firstName: String) {
        updateFieldAndValidate {
            copy(firstNameState = FieldState(firstName, validationManager.isValidName(firstName)))
        }
    }

    fun onLastNameChanged(lastName: String) {
        updateFieldAndValidate {
            copy(lastNameState = FieldState(lastName, validationManager.isValidName(lastName)))
        }
    }

    fun onPasswordChanged(password: String) {
        updateFieldAndValidate {
            copy(passwordState = FieldState(password, validationManager.isValidPassword(password)))
        }
    }

    fun onNumberChanged(number: String) {
        updateFieldAndValidate {
            copy(numberState = FieldState(number, validationManager.isValidNumber(number)))
        }
    }

    fun onConfirmPasswordChanged(confirmPassword: String) {
        updateFieldAndValidate {
            copy(
                confirmPasswordState = FieldState(
                    confirmPassword,
                    validationManager.arePasswordsMatching(passwordState.value, confirmPassword)
                )
            )
        }
    }

    private fun updateFieldAndValidate(update: RegisterFormState.() -> RegisterFormState) {
        viewModelScope.launch {
            _formState.update{
                it.update()
            }
            validateFields()
        }
    }

    private fun validateFields() {
        val currentState = _formState.value
        val isRegisterBtnActive = currentState.loginState.isValid &&
                currentState.firstNameState.isValid &&
                currentState.lastNameState.isValid &&
                currentState.passwordState.isValid &&
                currentState.numberState.isValid &&
                currentState.confirmPasswordState.isValid

        _formState.update {
            it.copy(isRegisterBtnActive = isRegisterBtnActive)
        }
    }
}
