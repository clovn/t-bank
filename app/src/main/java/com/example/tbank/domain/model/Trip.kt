package com.example.tbank.domain.model

import java.time.LocalDateTime

data class Trip(
    val id: Long,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val name: String,
    val participantsCount: Int,
    val budget: Int
)