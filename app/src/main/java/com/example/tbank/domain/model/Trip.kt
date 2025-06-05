package com.example.tbank.domain.model

import java.time.LocalDate

data class Trip(
    val id: Long,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val name: String,
    val participantsCount: Int,
    val budget: Int
)