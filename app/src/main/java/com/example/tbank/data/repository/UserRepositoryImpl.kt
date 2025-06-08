package com.example.tbank.data.repository

import com.example.tbank.data.remote.UserApiService
import com.example.tbank.data.model.safeApiCall
import com.example.tbank.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApiService: UserApiService
): UserRepository {
    override suspend fun getUsersByPhoneNumber(number: String) = safeApiCall(Dispatchers.IO) {
        userApiService.getUsersByPhoneNumber(listOf(number))
    }
    
    override suspend fun getUser(id: Int) = safeApiCall(Dispatchers.IO) {
        userApiService.getUser(id)
    }

    override suspend fun registerFirebaseToken(token: String) = safeApiCall(Dispatchers.IO) {
        userApiService.registerFirebaseToken(token)
    }

}
