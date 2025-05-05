package com.example.tbank.domain.login

import com.example.tbank.data.model.ResultWrapper
import com.example.tbank.domain.model.User
import com.example.tbank.domain.repository.UserRepository
import com.example.tbank.presentation.login.LoginState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.log

class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend fun invoke(login: String, password: String): ResultWrapper<User> {
        return when(val response = userRepository.login(login, password)){
            is ResultWrapper.Success -> {
                val token = response.value.token

                ResultWrapper.Success(response.value.user)
            }
            is ResultWrapper.Error -> response
        }
    }
}
