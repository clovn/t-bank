package com.example.tbank.domain.repository

import com.example.tbank.data.model.ResultWrapper
import com.example.tbank.domain.model.Expense

interface ExpensesRepository {
    suspend fun getTripExpensesSum(tripId: Long): ResultWrapper<Int>

    suspend fun getTripExpenses(tripId: Long): ResultWrapper<List<Expense>>

    suspend fun saveExpense(tripId: Int, expense: Expense, userId: Int): ResultWrapper<Unit>
}