package com.example.tbank.domain.main

import com.example.tbank.domain.repository.TripRepository
import javax.inject.Inject

class GetTripInfoUseCase @Inject constructor(
    private val tripRepository: TripRepository
) {
    suspend fun invoke() = tripRepository.getActiveTrip()
}