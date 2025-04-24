package com.example.t_bank.domain.login

import com.example.t_bank.data.UserApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userApiService: UserApiService
) {

    suspend fun invoke(login: String, password: String){
        withContext(Dispatchers.IO){
            userApiService.login(login, password)
        }
    }
}