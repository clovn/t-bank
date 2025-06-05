package com.example.tbank.domain.repository

import com.example.tbank.data.model.LoginRequest
import com.example.tbank.data.model.LoginResponse
import com.example.tbank.data.model.ResultWrapper
import com.example.tbank.domain.model.User

interface UserRepository {
    suspend fun getUsersByPhoneNumber(number: String): ResultWrapper<List<User>>
  
    suspend fun getUser(): ResultWrapper<User>
}
