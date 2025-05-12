package com.example.tbank.data.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.util.UUID

class UuidInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestWithId = chain.request().newBuilder()
            .header("Request-ID", UUID.randomUUID().toString())
            .build()
        return chain.proceed(requestWithId)
    }
}
