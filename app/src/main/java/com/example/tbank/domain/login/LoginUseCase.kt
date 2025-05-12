package com.example.tbank.domain.login

import com.example.tbank.data.model.ResultWrapper
import com.example.tbank.domain.model.User
import com.example.tbank.domain.repository.TokensRepository
import com.example.tbank.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokensRepository: TokensRepository
) {

    suspend fun invoke(login: String, password: String): ResultWrapper<User> {
        return when(val response = authRepository.login(login, password)){
            is ResultWrapper.Success -> {
                tokensRepository.saveAccessToken(response.value.accessToken)
                tokensRepository.saveRefreshToken(response.value.refreshToken)

                ResultWrapper.Success(response.value.user)
            }
            is ResultWrapper.Error -> response
            is ResultWrapper.HttpError -> response
        }
    }
}
