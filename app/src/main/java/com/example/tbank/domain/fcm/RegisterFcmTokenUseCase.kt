package com.example.tbank.domain.fcm

import com.example.tbank.domain.repository.UserRepository
import javax.inject.Inject

class RegisterFcmTokenUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(token: String) = userRepository.registerFirebaseToken(token)
}