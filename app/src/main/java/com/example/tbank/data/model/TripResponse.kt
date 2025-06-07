package com.example.tbank.data.model

data class TripResponse(
    val id: Long,
    val name: String,
    val startDate: String,
    val endDate: String,
    //val participantsCount: Int,
    val totalBudget: Int
)