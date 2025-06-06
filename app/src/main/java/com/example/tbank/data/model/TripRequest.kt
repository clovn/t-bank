package com.example.tbank.data.model


data class TripRequest(
    val name: String,
    val startDate: String,
    val endDate: String,
    val totalBudget: Int,
    val participants: List<String>
)