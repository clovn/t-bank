package com.example.tbank.data.repository.mock

import com.example.tbank.data.model.JwtTokens
import com.example.tbank.data.model.LoginRequest
import com.example.tbank.data.model.LoginResponse
import com.example.tbank.data.model.ResultWrapper
import com.example.tbank.data.model.TokenResponse
import com.example.tbank.data.model.UserDto
import com.example.tbank.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryMockImpl @Inject constructor(): AuthRepository {

    companion object{
        private const val ACCESS_TOKEN = "a_token"
        private const val REFRESH_TOKEN = "r_token"
    }

    override suspend fun login(login: String, password: String): ResultWrapper<LoginResponse> {
        val loginRequest = LoginRequest(login, password)
        if(loginRequest == LoginRequest("admin", "password")){
            return ResultWrapper.Success(LoginResponse(
                UserDto(
                    id = 1,
                    firstName = "Айрат",
                    lastName = "Халиуллин",
                    phoneNumber = "79376215272"
                ),
                JwtTokens(
                    accessToken = ACCESS_TOKEN,
                    refreshToken = REFRESH_TOKEN
                )
            ))
        } else {
            error("Неверный логин или пароль")
        }
    }


    override suspend fun register(
        firstName: String,
        lastName: String,
        number: String,
        password: String
    ): ResultWrapper<LoginResponse> {
        return ResultWrapper.Success(
            LoginResponse(
                UserDto(
                    id = 1,
                    firstName = "Айрат",
                    lastName = "Халиуллин",
                    phoneNumber = "79376215272"
                ),
                JwtTokens(
                    accessToken = ACCESS_TOKEN,
                    refreshToken = REFRESH_TOKEN
                )
            )
        )
    }

    override suspend fun refresh(): ResultWrapper<TokenResponse> {
        return ResultWrapper.Success(TokenResponse(accessToken = ACCESS_TOKEN, refreshToken = REFRESH_TOKEN))
    }

    override suspend fun logout() = Unit
}
