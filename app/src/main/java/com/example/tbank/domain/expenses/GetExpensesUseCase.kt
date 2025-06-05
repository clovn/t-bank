package com.example.tbank.domain.expenses

import com.example.tbank.domain.repository.ExpensesRepository
import javax.inject.Inject

class GetExpensesUseCase @Inject constructor(
    private val expensesRepository: ExpensesRepository
) {
    suspend fun invoke(tripId: Long) = expensesRepository.getTripExpenses(tripId)
}