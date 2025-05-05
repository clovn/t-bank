package com.example.tbank.domain.register

import com.example.tbank.data.model.LoginResponse
import com.example.tbank.data.model.ResultWrapper
import com.example.tbank.domain.model.User
import com.example.tbank.domain.repository.UserRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend fun invoke(
        username: String,
        firstName: String,
        lastName: String,
        number: String,
        password: String
    ): ResultWrapper<User> {
        val result = userRepository.register(username, firstName, lastName, normalizePhoneNumber(number), password)
        return when(result){
            is ResultWrapper.Success<LoginResponse> -> {
                //save token
                val token = result.value.token
                ResultWrapper.Success(result.value.user)
            }
            is ResultWrapper.Error -> {
                result
            }
        }
    }

    private fun normalizePhoneNumber(number: String): String {
        return number.replace(Regex("\\D"), "")
    }
}