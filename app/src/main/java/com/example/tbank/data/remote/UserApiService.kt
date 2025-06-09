package com.example.tbank.data.remote

import com.example.tbank.data.model.FcmTokenRequest
import com.example.tbank.data.model.NotificationResponse
import com.example.tbank.data.model.NotificationsReadRequest
import com.example.tbank.domain.model.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApiService {
    @GET("user/byPhoneNumber")
    suspend fun getUsersByPhoneNumber(@Query("phoneNumbers") phoneNumbers: List<String>): List<User>

    @GET("user/{userId}")
    suspend fun getUser(@Path("userId") userId: Int): User

    @POST("fcm-token")
    suspend fun registerFirebaseToken(@Body token: FcmTokenRequest)

    @GET("me/notification?isRead=false")
    suspend fun getNotifications(): List<NotificationResponse>

    @PATCH("me/notification")
    suspend fun readNotifications(@Body request: NotificationsReadRequest)
}