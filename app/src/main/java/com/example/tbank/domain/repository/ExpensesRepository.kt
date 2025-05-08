package com.example.tbank.domain.repository

import com.example.tbank.data.model.ResultWrapper

interface ExpensesRepository {
    suspend fun getTripExpensesSum(tripId: Long): ResultWrapper<Int>
}