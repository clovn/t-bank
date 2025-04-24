package com.example.t_bank.domain.model

data class LoginResponse(
    val token: String,
    val user: User
)
