package com.example.tbank.data.repository.mock

import com.example.tbank.data.model.LoginRequest
import com.example.tbank.data.model.LoginResponse
import com.example.tbank.data.model.ResultWrapper
import com.example.tbank.data.model.TokenResponse
import com.example.tbank.domain.model.User
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
                accessToken = ACCESS_TOKEN,
                refreshToken = REFRESH_TOKEN,
                user = User(
                    id = 0,
                    username = "John",
                    firstName = "John",
                    lastName = "Doe",
                    number = "+79999999999"
                )
            ))
        } else {
            error("Неверный логин или пароль")
        }
    }

    override suspend fun register(
        username: String,
        firstName: String,
        lastName: String,
        number: String,
        password: String
    ): ResultWrapper<LoginResponse> {
        return ResultWrapper.Success(
            LoginResponse(
                accessToken = ACCESS_TOKEN,
                refreshToken = REFRESH_TOKEN,
                user = User(id = 1, username = username, firstName = firstName, lastName = lastName, number = number)
            )
        )
    }

    override suspend fun refresh(): ResultWrapper<TokenResponse> {
        return ResultWrapper.Success(TokenResponse(accessToken = ACCESS_TOKEN, refreshToken = REFRESH_TOKEN))
    }

    override suspend fun logout() = Unit
}
