package com.example.tbank.data.model

data class NotificationResponse(
    val id: Int,
    val message: String,
    val tripId: Int,
    val type: String
)