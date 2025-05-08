package com.example.tbank.data.repository

import com.example.tbank.data.model.safeApiCall
import com.example.tbank.data.remote.TripApiService
import com.example.tbank.domain.repository.TripRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class TripRepositoryImpl @Inject constructor(
    private val tripApiService: TripApiService
): TripRepository {
    override suspend fun getActiveTrip() = safeApiCall(Dispatchers.IO) {
        tripApiService.getActiveTrips()[0]
    }
}