package com.example.tbank.data.remote

import com.example.tbank.data.model.LoginRequest
import com.example.tbank.data.model.LoginResponse
import com.example.tbank.domain.repository.UserRepository
import retrofit2.http.Body
import retrofit2.http.POST


interface UserApiService{
    @POST("/user/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse
}
