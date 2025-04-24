package com.example.t_bank.domain.repository

import com.example.t_bank.domain.model.LoginRequest
import com.example.t_bank.domain.model.LoginResponse

interface UserRepository {
    suspend fun login(loginRequest: LoginRequest): LoginResponse
}