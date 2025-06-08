package com.example.tbank.domain.invite

import com.example.tbank.domain.repository.TripRepository
import javax.inject.Inject

class InviteAcceptOrRejectUseCase @Inject constructor(
    private val tripRepository: TripRepository
) {
    suspend fun invoke(tripId: Int, isAccept: Boolean) = tripRepository.invitationAction(tripId, isAccept)
}