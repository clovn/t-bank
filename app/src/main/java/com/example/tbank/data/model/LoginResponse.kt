package com.example.tbank.data.model

import com.example.tbank.domain.model.User

data class LoginResponse(
    val token: String,
    val user: User
)
