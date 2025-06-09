package com.example.tbank.presentation.model

import com.example.tbank.domain.model.Trip

data class TripInfo(
    val trip: Trip,
    val expensesSum: Int
)