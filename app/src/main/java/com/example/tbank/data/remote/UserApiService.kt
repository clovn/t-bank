package com.example.tbank.data.remote

import com.example.tbank.domain.model.User
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApiService {
    @GET("user/byPhoneNumber")
    suspend fun getUsersByPhoneNumber(@Query("phoneNumbers") phoneNumbers: List<String>): List<User>

    @GET("user/{userId}")
    suspend fun getUser(@Path("userId") userId: Int): User
}