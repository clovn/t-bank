package com.example.tbank.domain.repository

import com.example.tbank.data.model.LoginResponse
import com.example.tbank.data.model.ResultWrapper
import com.example.tbank.data.model.TokenResponse

interface AuthRepository {
    suspend fun login(login: String, password: String): ResultWrapper<LoginResponse>

    suspend fun register(
        username: String,
        firstName: String,
        lastName: String,
        number: String,
        password: String
    ): ResultWrapper<LoginResponse>

    suspend fun refresh(): ResultWrapper<TokenResponse>

    suspend fun logout()
}
