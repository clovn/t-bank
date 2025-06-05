package com.example.tbank.data.repository

import com.example.tbank.data.model.TripRequest
import com.example.tbank.domain.model.TripInfo
import com.example.tbank.domain.model.User
import com.example.tbank.domain.repository.TripRepository
import javax.inject.Inject
import com.example.tbank.data.model.safeApiCall
import com.example.tbank.data.remote.TripApiService
import kotlinx.coroutines.Dispatchers

class TripRepositoryImpl @Inject constructor(): TripRepository {

    private var trip: TripRequest? = null

    override suspend fun saveTripInfo(tripInfo: TripInfo) {
        trip = TripRequest(
            tripInfo.startDate,
            tripInfo.name,
            tripInfo.budget,
            listOf()
        )
    }

    override suspend fun saveParticipants(participants: Set<User>) {
        trip?.let {
            it.copy(
                participantsNumbers = participants.map { user ->  user.number }
            )
        }

    override suspend fun getActiveTrip() = safeApiCall(Dispatchers.IO) {
        tripApiService.getActiveTrips()[0]
    }
}