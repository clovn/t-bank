package com.example.tbank.data.repository

import com.example.tbank.data.model.safeApiCall
import com.example.tbank.data.remote.ExpensesApiService
import com.example.tbank.domain.model.CategoryType
import com.example.tbank.domain.model.Expense
import com.example.tbank.domain.repository.ExpensesRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class ExpensesRepositoryImpl @Inject constructor(
    private val expensesApiService: ExpensesApiService
): ExpensesRepository {
    override suspend fun getTripExpensesSum(tripId: Long) = safeApiCall(Dispatchers.IO) {
        expensesApiService.getTripExpensesSum(tripId)
    }

    override suspend fun getTripExpenses(tripId: Long) = safeApiCall(Dispatchers.IO) {
        expensesApiService.getTripExpenses(tripId).map {
            Expense(it.id, it.name, CategoryType.valueOf(it.category.name), it.amount, it.author.firstName)
        }
    }
}