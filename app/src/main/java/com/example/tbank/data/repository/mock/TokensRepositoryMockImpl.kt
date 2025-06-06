package com.example.tbank.data.repository.mock

import com.example.tbank.domain.repository.TokensRepository
import javax.inject.Inject

class TokensRepositoryMockImpl @Inject constructor() : TokensRepository {

    override suspend fun saveAccessToken(accessToken: String) = Unit

    override suspend fun saveRefreshToken(refreshToken: String) = Unit

    override suspend fun isLogged() = true

    override suspend fun getAccessToken() = "a_token"

    override suspend fun getRefreshToken() = "r_token"
    override suspend fun clearTokens() = Unit
    override suspend fun saveId(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getId(): Int = 1
}
