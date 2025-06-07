package com.example.tbank.domain.main

import com.example.tbank.domain.repository.TokensRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val tokensRepository: TokensRepository
) {

    suspend fun invoke(){
        tokensRepository.clearTokens()
    }
}