package com.example.tbank.data.repository.mock

import com.example.tbank.data.model.ResultWrapper
import com.example.tbank.domain.model.User
import com.example.tbank.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryMockImpl @Inject constructor(): UserRepository{
    override suspend fun getUsersByPhoneNumber(number: String) = ResultWrapper.Success(
        listOf(
            User(
                id = 0,
                firstName = "Айрат",
                lastName = "Халиуллин",
                number = "79999999999"
            )
        )
    )
    
    companion object {
        private val user = User(
            id = 0,
            firstName = "Айрат",
            lastName = "Халиуллин",
            number = "79999999999"
        )
    }

    override suspend fun getUser(id: Int): ResultWrapper<User> {
        return ResultWrapper.Success(
            value = user
        )
    }
}
