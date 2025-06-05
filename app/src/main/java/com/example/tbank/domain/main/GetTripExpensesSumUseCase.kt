package com.example.tbank.domain.main

import com.example.tbank.domain.repository.ExpensesRepository
import javax.inject.Inject

class GetTripExpensesSumUseCase @Inject constructor(
    private val expensesRepository: ExpensesRepository
) {
    suspend fun invoke(tripId: Long) = expensesRepository.getTripExpensesSum(tripId)
}