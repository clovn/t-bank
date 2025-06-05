package com.example.tbank.data.remote

import com.example.tbank.domain.model.User
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApiService {
    @GET("user/phoneNumber")
    suspend fun getUsersByPhoneNumber(@Query("phoneNumber") phoneNumber: String): List<User>
}