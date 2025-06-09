package com.example.tbank.data.model

import com.example.tbank.domain.model.User

data class Expense(
    val id: Long,
    val categoryId: Int,
    val amount: Int,
    val description: String,
    val payerId: Int,
    val phoneNumbersOfDebtors: List<String>?
)


data class ExpensesResponse(
    val actualExpenses: List<Expense>,
    val payers: List<User>
)

data class ExpensesPlannedResponse(
    val plannedExpenses: List<Expense>
)