package com.example.tbank.domain.expenses

import com.example.tbank.domain.model.Expense
import com.example.tbank.domain.repository.ExpensesRepository
import com.example.tbank.domain.repository.TokensRepository
import javax.inject.Inject

class SaveExpenseUseCase @Inject constructor(
    private val expensesRepository: ExpensesRepository,
    private val tokensRepository: TokensRepository
) {

    suspend fun invoke(tripId: Int, expense: Expense) = expensesRepository.saveExpense(tripId, expense, tokensRepository.getId()!!)
}