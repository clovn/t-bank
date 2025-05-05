package com.example.tbank.data.model

data class RegisterRequest(
    val username: String,
    val firstName: String,
    val lastName: String,
    val number: String,
    val password: String
)