package com.example.tbank.domain.model

data class NotificationModel(
    val id: Int,
    val message: String,
    val tripId: Int,
    val type: NotificationType
)
