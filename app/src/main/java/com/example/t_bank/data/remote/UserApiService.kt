package com.example.t_bank.data.remote

import com.example.t_bank.domain.model.LoginRequest
import com.example.t_bank.domain.model.LoginResponse
import com.example.t_bank.domain.repository.UserRepository
import retrofit2.http.Body
import retrofit2.http.POST


interface UserApiService: UserRepository {
    @POST("/user/login")
    override suspend fun login(@Body loginRequest: LoginRequest): LoginResponse
}