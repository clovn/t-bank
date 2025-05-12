package com.example.tbank.domain.register

import com.example.tbank.data.model.LoginResponse
import com.example.tbank.data.model.ResultWrapper
import com.example.tbank.domain.model.User
import com.example.tbank.domain.repository.TokensRepository
import com.example.tbank.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokensRepository: TokensRepository
) {

    suspend fun invoke(
        username: String,
        firstName: String,
        lastName: String,
        number: String,
        password: String
    ): ResultWrapper<User> {
        val result = authRepository.register(username, firstName, lastName, normalizePhoneNumber(number), password)
        return when(result){
            is ResultWrapper.Success<LoginResponse> -> {
                tokensRepository.saveAccessToken(result.value.accessToken)
                tokensRepository.saveRefreshToken(result.value.refreshToken)

                ResultWrapper.Success(result.value.user)
            }
            is ResultWrapper.Error -> {
                result
            }
            is ResultWrapper.HttpError -> {
                result
            }
        }
    }

    private fun normalizePhoneNumber(number: String): String {
        return number.replace(Regex("\\D"), "")
    }
}
