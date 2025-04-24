package com.example.t_bank.data.repository.mock

import com.example.t_bank.domain.model.LoginRequest
import com.example.t_bank.domain.model.LoginResponse
import com.example.t_bank.domain.model.User
import com.example.t_bank.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryMockImpl @Inject constructor(): UserRepository {
    override suspend fun login(loginRequest: LoginRequest): LoginResponse {
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
            throw Exception("Неверный логин или пароль")
        }
    }
}