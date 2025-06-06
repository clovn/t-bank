package com.example.tbank.data.repository.mock

import com.example.tbank.data.model.ResultWrapper
import com.example.tbank.domain.model.Category
import com.example.tbank.domain.model.Trip
import com.example.tbank.domain.model.TripInfo
import com.example.tbank.domain.model.User
import com.example.tbank.domain.repository.TripRepository
import java.time.LocalDate
import javax.inject.Inject

class TripRepositoryMockImpl @Inject constructor(): TripRepository {
    override suspend fun saveTripInfo(tripInfo: TripInfo) {

    }

    override suspend fun saveParticipants(participants: Set<User>) {

    }

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

    override suspend fun saveTrip(categories: List<Category>): ResultWrapper<Unit> {
        TODO("Not yet implemented")
    }

    override fun getTripBudget() = 1200000
}