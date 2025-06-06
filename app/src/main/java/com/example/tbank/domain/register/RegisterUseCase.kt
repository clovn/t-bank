package com.example.tbank.domain.register

import com.example.tbank.data.model.LoginResponse
import com.example.tbank.data.model.ResultWrapper
import com.example.tbank.domain.model.User
import com.example.tbank.domain.repository.TokensRepository
import com.example.tbank.domain.repository.AuthRepository
import com.example.tbank.presentation.normalizePhoneNumber
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokensRepository: TokensRepository
) {

    suspend fun invoke(
        firstName: String,
        lastName: String,
        number: String,
        password: String
    ): ResultWrapper<User> {
        val result = authRepository.register(firstName, lastName, normalizePhoneNumber(number), password)
        return when(result){
            is ResultWrapper.Success<LoginResponse> -> {
                tokensRepository.saveAccessToken(result.value.jwtTokenPairDto.accessToken)
                tokensRepository.saveRefreshToken(result.value.jwtTokenPairDto.refreshToken)
                tokensRepository.saveId(result.value.userDto.id)

                ResultWrapper.Success(
                    User(
                        id = result.value.userDto.id,
                        firstName = result.value.userDto.firstName,
                        lastName = result.value.userDto.lastName,
                        number = result.value.userDto.phoneNumber
                    )
                )
            }
            is ResultWrapper.Error -> {
                result
            }
            is ResultWrapper.HttpError -> {
                result
            }
        }
    }
}
