package com.example.tbank.domain.login

import com.example.tbank.data.model.ResultWrapper
import com.example.tbank.domain.fcm.RegisterFcmTokenUseCase
import com.example.tbank.domain.model.User
import com.example.tbank.domain.repository.TokensRepository
import com.example.tbank.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokensRepository: TokensRepository,
    private val registerFcmTokenUseCase: RegisterFcmTokenUseCase
) {

    suspend fun invoke(number: String, password: String): ResultWrapper<User> {
        return when(val response = authRepository.login(number, password)){
            is ResultWrapper.Success -> {
                tokensRepository.saveAccessToken(response.value.jwtTokenPairDto.accessToken)
                tokensRepository.saveRefreshToken(response.value.jwtTokenPairDto.refreshToken)
                tokensRepository.saveId(response.value.userDto.id)

                registerFcmTokenUseCase.invoke(tokensRepository.getFcmToken()!!)

                ResultWrapper.Success(
                    User(
                        id = response.value.userDto.id,
                        firstName = response.value.userDto.firstName,
                        lastName = response.value.userDto.lastName,
                        number = response.value.userDto.phoneNumber
                    )
                )
            }
            is ResultWrapper.Error -> response
            is ResultWrapper.HttpError -> response
        }
    }
}
