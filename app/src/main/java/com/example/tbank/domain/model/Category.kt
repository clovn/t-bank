package com.example.tbank.domain.model

data class Category(
    val type: CategoryType,
    val amount: Int,
    val percent: Int = 0
)