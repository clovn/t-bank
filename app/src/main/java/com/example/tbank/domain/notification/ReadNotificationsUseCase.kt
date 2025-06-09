package com.example.tbank.domain.notification

import com.example.tbank.domain.repository.UserRepository
import javax.inject.Inject

class ReadNotificationsUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun invoke(notificationsId: List<Int>) = userRepository.readNotifications(notificationsId)
}