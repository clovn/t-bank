package com.example.tbank.data.repository

import com.example.tbank.data.model.safeApiCall
import com.example.tbank.data.remote.ExpensesApiService
import com.example.tbank.domain.repository.ExpensesRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class ExpensesRepositoryImpl @Inject constructor(
    private val expensesApiService: ExpensesApiService
): ExpensesRepository {
    override suspend fun getTripExpensesSum(tripId: Long) = safeApiCall(Dispatchers.IO) {
        expensesApiService.getTripExpensesSum(tripId)
    }
}