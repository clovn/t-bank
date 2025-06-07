package com.example.tbank.data.repository

import android.util.Log
import com.example.tbank.data.model.CategoryRequest
import com.example.tbank.data.model.ExpenseRequest
import com.example.tbank.data.model.safeApiCall
import com.example.tbank.data.remote.ExpensesApiService
import com.example.tbank.domain.model.Category
import com.example.tbank.domain.model.CategoryType
import com.example.tbank.domain.model.Expense
import com.example.tbank.domain.model.User
import com.example.tbank.domain.repository.ExpensesRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class ExpensesRepositoryImpl @Inject constructor(
    private val expensesApiService: ExpensesApiService
): ExpensesRepository {
    override suspend fun getTripExpensesSum(tripId: Long) = safeApiCall(Dispatchers.IO) {
        expensesApiService.getTripExpensesSum(tripId).sumOf { it.expenseSum }
    }

    override suspend fun getTripExpenses(tripId: Long) = safeApiCall(Dispatchers.IO) {
        val response = expensesApiService.getTripExpenses(tripId)
        response.actualExpenses.map {
            Log.d("adawdawdawdawd", response.toString())
            Expense(
                it.id,
                it.description,
                CategoryType.entries[it.categoryId],
                it.amount,
                response.payers[it.payerId].firstName,
                it.phoneNumbersOfDebtors?.map { phoneNumber ->
                    response.payers.find { user -> user.number == phoneNumber } ?: User(id = -1, firstName = "", lastName = "", number = "")
                }
            )
        }
    }

    suspend fun addCategory(category: Category, tripId: Int) = safeApiCall(Dispatchers.IO) {
        expensesApiService.addCategory(tripId, CategoryRequest(
            categoryId = mapCategoryTypeId(category.type),
            amount = category.amount
        )
        )
    }

    private fun mapCategoryTypeId(type: CategoryType) = when(type){
        CategoryType.Tickets -> 0
        CategoryType.Hotel -> 1
        CategoryType.Food -> 2
        CategoryType.Entertainment -> 3
        CategoryType.Insurance -> 4
        CategoryType.More -> 5
    }

    override suspend fun saveExpense(tripId: Int, expense: Expense, userId: Int) = safeApiCall(Dispatchers.IO){
        expensesApiService.saveExpense(tripId,
            ExpenseRequest(
                categoryId = mapCategoryTypeId(expense.type),
                amount = expense.amount,
                description =  expense.name,
                status = "ACTUAL",
                payerId = userId,
                phoneNumbersOfDebtors = expense.debtors?.map { user -> user.number } ?: emptyList()
            )
        )
    }
}