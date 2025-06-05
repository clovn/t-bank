package com.example.tbank.data.model

import java.time.LocalDate

data class TripRequest(
    val startDate: LocalDate,
    val name: String,
    val budget: Int,
    val participantsNumbers: List<String>
)