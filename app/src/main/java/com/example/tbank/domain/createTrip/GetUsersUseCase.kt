package com.example.tbank.domain.createTrip

import com.example.tbank.domain.repository.UserRepository
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend fun invoke(query: String) = userRepository.getUsersByPhoneNumber(query)
}