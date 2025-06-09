package com.example.tbank.domain.expenses

import com.example.tbank.domain.repository.ExpensesRepository
import javax.inject.Inject

class GetTripPlannedExpensesUseCase @Inject constructor(
    private val expensesRepository: ExpensesRepository
) {
    suspend fun invoke(tripId: Int) = expensesRepository.getTripPlannedExpenses(tripId)
}