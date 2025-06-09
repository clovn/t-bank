package com.example.tbank.domain.repository

import com.example.tbank.data.model.ResultWrapper
import com.example.tbank.domain.model.Category
import com.example.tbank.domain.model.Trip
import com.example.tbank.domain.model.TripInfo
import com.example.tbank.domain.model.User

interface TripRepository {
    suspend fun saveTripInfo(tripInfo: TripInfo)

    suspend fun saveParticipants(participants: Set<User>)

    suspend fun getActiveTrip(): ResultWrapper<List<Trip>>

    suspend fun saveTrip(categories: List<Category>): ResultWrapper<Unit>

    fun getTripBudget(): Int

    suspend fun getParticipants(tripId: Int): ResultWrapper<List<User>>

    suspend fun invitationAction(tripId: Int, isAccept: Boolean): ResultWrapper<Unit>
}