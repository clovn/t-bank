package com.example.tbank.domain.model

data class Balance(
    val id: Int,
    val debtor: User,
    val amount: Int
)