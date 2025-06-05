package com.example.tbank.domain.model

import java.time.LocalDate

data class TripInfo(
    val startDate: LocalDate,
    val endDate: LocalDate,
    val name: String,
    val budget: Int
)