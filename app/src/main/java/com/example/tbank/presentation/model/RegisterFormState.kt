package com.example.tbank.presentation.model

data class RegisterFormState(
    val loginState: FieldState = FieldState(),
    val firstNameState: FieldState = FieldState(),
    val lastNameState: FieldState = FieldState(),
    val passwordState: FieldState = FieldState(),
    val numberState: FieldState = FieldState(),
    val confirmPasswordState: FieldState = FieldState(),
    val isRegisterBtnActive: Boolean = false
)
