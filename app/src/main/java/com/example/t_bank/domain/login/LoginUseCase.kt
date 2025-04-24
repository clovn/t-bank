package com.example.t_bank.domain.login

import com.example.t_bank.domain.model.LoginRequest
import com.example.t_bank.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend fun invoke(login: String, password: String){
        withContext(Dispatchers.IO){
            userRepository.login(LoginRequest(login, password))
        }
    }
}