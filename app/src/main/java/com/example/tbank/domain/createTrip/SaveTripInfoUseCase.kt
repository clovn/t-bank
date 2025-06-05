package com.example.tbank.domain.createTrip

import com.example.tbank.domain.model.TripInfo
import com.example.tbank.domain.repository.TripRepository
import javax.inject.Inject

class SaveTripInfoUseCase @Inject constructor(
    private val tripRepository: TripRepository
) {

    suspend fun invoke(tripInfo: TripInfo) = tripRepository.saveTripInfo(tripInfo)
}