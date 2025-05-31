package com.example.tbank.domain.model

data class Expense(
    val id: Long,
    val name: String,
    val type: CategoryType,
    val amount: Int,
    val authorName: String
)