package com.example.tbank.domain.createTrip

import com.example.tbank.domain.repository.TripRepository
import javax.inject.Inject

class GetTripBudgetUseCase @Inject constructor(
    private val tripRepository: TripRepository
) {
    fun invoke() = tripRepository.getTripBudget()
}