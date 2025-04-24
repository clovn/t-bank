package com.example.t_bank.data.repository

import com.example.t_bank.data.remote.UserApiService
import com.example.t_bank.domain.model.LoginRequest
import com.example.t_bank.domain.model.LoginResponse
import com.example.t_bank.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApiService: UserApiService
): UserRepository {
    override suspend fun login(loginRequest: LoginRequest): LoginResponse {
        return userApiService.login(loginRequest)
    }
}