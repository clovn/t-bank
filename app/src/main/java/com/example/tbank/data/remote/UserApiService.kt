package com.example.tbank.data.remote

import com.example.tbank.domain.model.User
import retrofit2.http.GET

interface UserApiService {
    @GET("/user")
    suspend fun getUser(): User
}