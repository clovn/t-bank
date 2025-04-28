package com.example.tbank.domain.login

import com.example.tbank.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend fun invoke(login: String, password: String){
        withContext(Dispatchers.IO){
            userRepository.login(login, password)
        }
    }
}
