package com.example.tbank.data.repository.mock

import com.example.tbank.data.model.ResultWrapper
import com.example.tbank.domain.model.Trip
import com.example.tbank.domain.repository.TripRepository
import java.time.LocalDate
import javax.inject.Inject

class TripRepositoryMockImpl @Inject constructor(): TripRepository {
    override suspend fun getActiveTrip() = ResultWrapper.Success(
        Trip(
            id = 2,
            startDate = LocalDate.of(2025, 5, 12),
            endDate = LocalDate.of(2025, 5, 21),
            name = "Some trip",
            participantsCount = 5,
            budget = 1200000
        )
    )
}