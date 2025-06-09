package com.example.tbank.presentation.model

data class TripInfoView(
    val id: Long,
    val name: String,
    val date: String,
    val participantsCount: Int,
    val budget: Int,
    val budgetString: String,
    val expenses: String,
    val progress: Int = 0
)