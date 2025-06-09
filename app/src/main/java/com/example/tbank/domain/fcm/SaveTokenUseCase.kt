package com.example.tbank.domain.fcm

import com.example.tbank.domain.repository.TokensRepository
import javax.inject.Inject

class SaveTokenUseCase @Inject constructor(
    private val tokensRepository: TokensRepository
) {
    suspend operator fun invoke(fcmToken: String) = tokensRepository.saveFcmToken(fcmToken)
}