package com.example.tbank.data.model

data class CategoryRequest(
    val id: Int = 0,
    val categoryId: Int,
    val amount: Int,
    val status: String = "PLANNED",
)