package com.example.tbank.domain.repository


interface TokensRepository {
    suspend fun saveAccessToken(accessToken: String)
    suspend fun saveRefreshToken(refreshToken: String)
    suspend fun isLogged(): Boolean
    suspend fun getAccessToken(): String?
    suspend fun getRefreshToken(): String?
    suspend fun clearTokens()
    suspend fun saveId(id: Int)
    suspend fun getId(): Int?
    suspend fun saveFcmToken(fcmToken: String)
    suspend fun getFcmToken(): String?
}
