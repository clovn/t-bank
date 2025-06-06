package com.example.tbank.data.model

data class RegisterRequest(
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val password: String,
    val fcmToken: String
)
