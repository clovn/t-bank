package com.example.tbank.data.repository

import com.example.tbank.data.remote.UserApiService
import com.example.tbank.data.model.LoginRequest
import com.example.tbank.data.model.LoginResponse
import com.example.tbank.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApiService: UserApiService
): UserRepository {
    override suspend fun login(login: String, password: String): LoginResponse {
        return userApiService.login(LoginRequest(login, password))
    }
}
