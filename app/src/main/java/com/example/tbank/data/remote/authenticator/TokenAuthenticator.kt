package com.example.tbank.data.remote.authenticator

import com.example.tbank.data.model.ResultWrapper
import com.example.tbank.data.model.TokenResponse
import com.example.tbank.domain.repository.AuthRepository
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator(
    private val authRepository: AuthRepository,
    private val mutex: Mutex,
) : Authenticator {
    @Volatile
    private var accessToken: String? = null
    @Volatile
    private var isLogoutStarted = false
    private val maxRetries = 3

    override fun authenticate(route: Route?, response: Response): Request? = runBlocking {
        if (isLogoutStarted) return@runBlocking null


        val requestId = response.request().header("Request-ID") ?: return@runBlocking null
        val retryCount = response.request().header("Retry-Count")?.toIntOrNull() ?: 0

        if (retryCount >= maxRetries) {
            authRepository.logout()
            isLogoutStarted = true
            return@runBlocking null
        }

        return@runBlocking if (mutex.holdsLock(requestId)) {
            handleTokenRefresh(response, retryCount)
        } else {
            mutex.lock(requestId)
            handleTokenRefresh(response, retryCount)
        }
    }

    private suspend fun handleTokenRefresh(response: Response, retryCount: Int): Request? {
        accessToken = getNewAccessToken()

        return response.request().newBuilder()
            .header("Authorization", "Bearer $accessToken")
            .header("Retry-Count", (retryCount + 1).toString())
            .build()
    }

    private suspend fun getNewAccessToken(): String? {
        return when(val response = authRepository.refresh()){
            is ResultWrapper.Success<TokenResponse> -> response.value.accessToken
            else -> null
        }
    }
}
