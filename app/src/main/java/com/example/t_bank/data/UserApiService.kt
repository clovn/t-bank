package com.example.t_bank.data

import com.example.t_bank.domain.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST


interface UserApiService {
    @POST("/user/login")
    suspend fun login(@Body login: String, password: String): LoginResponse
}