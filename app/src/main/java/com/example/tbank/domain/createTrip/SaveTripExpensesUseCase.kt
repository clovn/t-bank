package com.example.tbank.domain.createTrip

import com.example.tbank.domain.model.Category
import com.example.tbank.domain.repository.TripRepository
import javax.inject.Inject

class SaveTripExpensesUseCase @Inject constructor(
    private val tripRepository: TripRepository
) {
    suspend fun invoke(categories: List<Category>) = tripRepository.saveTrip(categories)
}