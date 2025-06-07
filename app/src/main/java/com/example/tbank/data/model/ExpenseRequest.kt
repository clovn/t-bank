package com.example.tbank.data.model

data class ExpenseRequest(
    val categoryId: Int,
    val amount: Int,
    val description: String = "",
    val status: String = "PLANNED",
    val payerId: Int,
    val phoneNumbersOfDebtors: List<String>
)