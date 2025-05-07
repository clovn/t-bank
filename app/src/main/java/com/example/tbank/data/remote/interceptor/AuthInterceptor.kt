package com.example.tbank.data.remote.interceptor

import com.example.tbank.domain.repository.TokensRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokensRepository: TokensRepository,
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        if ("false" == request.header("Auth")) {
            return chain.proceed(request)
        }

        val newRequest = request.newBuilder()
        val token = runBlocking {
            tokensRepository.getAccessToken()
        }

        newRequest.addHeader("Authorization", "Bearer $token")


        return chain.proceed(newRequest.build())
    }
}
