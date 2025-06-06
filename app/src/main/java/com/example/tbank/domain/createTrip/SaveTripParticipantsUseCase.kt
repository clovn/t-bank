package com.example.tbank.domain.createTrip

import com.example.tbank.domain.model.User
import com.example.tbank.domain.repository.TripRepository
import javax.inject.Inject

class SaveTripParticipantsUseCase @Inject constructor(
    private val tripRepository: TripRepository
) {

    suspend fun invoke(participants: Set<User>) = tripRepository.saveParticipants(participants)
}