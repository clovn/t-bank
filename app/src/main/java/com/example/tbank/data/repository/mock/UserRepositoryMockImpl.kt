package com.example.tbank.data.repository.mock

import com.example.tbank.data.model.LoginRequest
import com.example.tbank.data.model.LoginResponse
import com.example.tbank.data.model.User
import com.example.tbank.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryMockImpl @Inject constructor(): UserRepository {
    override suspend fun login(login: String, password: String): LoginResponse {
        val loginRequest = LoginRequest(login, password)
        if(loginRequest.equals(LoginRequest("admin", "password"))){
            return LoginResponse(
                token = "wadawd",
                user = User(
                    id = 0,
                    username = "awdawd",
                    firstName = "awdadw",
                    lastName = "awdwa",
                    number = "+79999999999"
                )
            )
        } else {
            error("Неверный логин или пароль")
        }
    }
}
