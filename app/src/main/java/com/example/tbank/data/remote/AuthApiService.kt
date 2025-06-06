package com.example.tbank.data.remote

import com.example.tbank.data.model.LoginRequest
import com.example.tbank.data.model.LoginResponse
import com.example.tbank.data.model.RegisterRequest
import com.example.tbank.data.model.TokenResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApiService{
    @POST("user/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @POST("user")
    suspend fun register(@Body registerRequest: RegisterRequest): LoginResponse

    @GET("refresh")
    suspend fun refreshToken(@Header("Authorization") refreshToken: String?): TokenResponse
}
