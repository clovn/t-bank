package com.example.tbank.domain.repository

import com.example.tbank.domain.model.TripInfo
import com.example.tbank.domain.model.User

interface TripRepository {
    suspend fun saveTripInfo(tripInfo: TripInfo)

    suspend fun saveParticipants(participants: Set<User>)

    suspend fun getActiveTrip(): ResultWrapper<Trip?>
}