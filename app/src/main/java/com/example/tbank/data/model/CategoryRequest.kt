package com.example.tbank.data.model

data class CategoryRequest(
    val categoryId: Int,
    val amount: Int,
    val status: String = "PLANNED"
)