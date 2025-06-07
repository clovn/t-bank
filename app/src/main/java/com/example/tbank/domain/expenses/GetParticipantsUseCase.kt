package com.example.tbank.domain.expenses

import com.example.tbank.domain.repository.TripRepository
import javax.inject.Inject

class GetParticipantsUseCase @Inject constructor(
    private val tripRepository: TripRepository
) {
    suspend fun invoke(tripId: Int) = tripRepository.getParticipants(tripId)
}