package com.example.tbank.data.repository

import com.example.tbank.data.remote.UserApiService
import com.example.tbank.data.model.LoginRequest
import com.example.tbank.data.model.LoginResponse
import com.example.tbank.data.model.RegisterRequest
import com.example.tbank.data.model.ResultWrapper
import com.example.tbank.data.model.safeApiCall
import com.example.tbank.domain.model.User
import com.example.tbank.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApiService: UserApiService
): UserRepository {
    override suspend fun getUsersByPhoneNumber(number: String) = safeApiCall(Dispatchers.IO) {
        userApiService.getUsersByPhoneNumber(number)
    }
    
    override suspend fun getUser() = safeApiCall(Dispatchers.IO) {
        userApiService.getUser()
    }

}
