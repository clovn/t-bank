package com.example.tbank.domain.model

import com.google.gson.annotations.SerializedName

data class User(
    val id: Int,
    val firstName: String,
    val lastName: String,
    @SerializedName("phoneNumber")
    val number: String
)
