package com.example.tbank.domain.model

import java.time.LocalDate

data class Trip(
    val id: Long,
    val name: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val participantsCount: Int,
    val budget: Int
)