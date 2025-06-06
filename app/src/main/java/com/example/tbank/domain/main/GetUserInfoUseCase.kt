package com.example.tbank.domain.main

import com.example.tbank.domain.repository.TokensRepository
import com.example.tbank.domain.repository.UserRepository
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val tokensRepository: TokensRepository
) {
    suspend fun invoke() = userRepository.getUser(tokensRepository.getId()!!)
}