package com.example.tbank.data.repository

import com.example.tbank.data.remote.AuthApiService
import com.example.tbank.data.model.LoginRequest
import com.example.tbank.data.model.RegisterRequest
import com.example.tbank.data.model.safeApiCall
import com.example.tbank.domain.repository.AuthRepository
import com.example.tbank.domain.repository.TokensRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApiService: AuthApiService,
    private val tokensRepository: TokensRepository
): AuthRepository {
    override suspend fun login(number: String, password: String) = safeApiCall(Dispatchers.IO) {
        authApiService.login(LoginRequest(number, password))
    }

    override suspend fun register(
        firstName: String,
        lastName: String,
        number: String,
        password: String
    ) = safeApiCall(Dispatchers.IO) {
        authApiService.register(RegisterRequest(firstName, lastName, number, password, "awdawd"))
    }

    override suspend fun refresh() = safeApiCall(Dispatchers.IO) {
        val response = authApiService.refreshToken("Bearer ${tokensRepository.getRefreshToken()}")
        tokensRepository.saveAccessToken(response.accessToken)
        tokensRepository.saveRefreshToken(response.refreshToken)
        response
    }

    override suspend fun logout() {
        withContext(Dispatchers.IO){
            tokensRepository.clearTokens()
        }
    }
}
