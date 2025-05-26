package com.example.tbank.data.repository.mock

import com.example.tbank.data.model.ResultWrapper
import com.example.tbank.domain.model.CategoryType
import com.example.tbank.domain.model.Expense
import com.example.tbank.domain.repository.ExpensesRepository
import javax.inject.Inject

class ExpensesRepositoryMockImpl @Inject constructor(): ExpensesRepository {
    override suspend fun getTripExpensesSum(tripId: Long) = ResultWrapper.Success(875000)

    override suspend fun getTripExpenses(tripId: Long) = ResultWrapper.Success(
        listOf(
            Expense(1, "Билеты на самолет", CategoryType.Tickets, 200000, "Айрат"),
            Expense(2, "Отель", CategoryType.Hotel, 200000, "Айрат"),
            Expense(2, "Питание", CategoryType.Food, 100000, "Айрат"),
            Expense(3, "Развлечения", CategoryType.Entertainment, 100000, "Айрат"),
            Expense(4, "Страховка", CategoryType.Insurance, 100000, "Айрат"),
            Expense(5, "Остальное", CategoryType.More, 175000, "Айрат"),
        )
    )
}