package com.example.tbank.domain.repository

import com.example.tbank.data.model.ResultWrapper
import com.example.tbank.domain.model.NotificationModel
import com.example.tbank.domain.model.User

interface UserRepository {
    suspend fun getUsersByPhoneNumber(number: String): ResultWrapper<List<User>>
  
    suspend fun getUser(id: Int): ResultWrapper<User>

    suspend fun registerFirebaseToken(token: String): ResultWrapper<Unit>

    suspend fun getNotifications(): ResultWrapper<List<NotificationModel>>

    suspend fun readNotifications(notificationsId: List<Int>): ResultWrapper<Unit>
}
