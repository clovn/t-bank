package com.example.tbank.domain.repository

import com.example.tbank.data.model.LoginRequest
import com.example.tbank.data.model.LoginResponse

interface UserRepository {
    suspend fun login(login: String, password: String): LoginResponse
}
