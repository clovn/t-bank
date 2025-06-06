package com.example.tbank.domain.login

import com.example.tbank.domain.repository.TokensRepository
import javax.inject.Inject

class IsLoggedUseCase @Inject constructor(
    private val tokensRepository: TokensRepository
) {
    suspend fun invoke() = tokensRepository.isLogged()
}