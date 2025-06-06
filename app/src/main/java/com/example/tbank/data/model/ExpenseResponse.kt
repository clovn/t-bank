package com.example.tbank.data.model

data class ExpenseResponse(
    val id: Long,
    val amount: Int,
    val name: String,
    val author: Author,
    val category: Category
)

data class Author(
    val id: Long,
    val username: String,
    val firstName: String,
)

data class Category(
    val id: Long,
    val name: String,
)
