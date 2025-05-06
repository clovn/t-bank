package com.example.tbank.domain.repository

import com.example.tbank.data.model.ResultWrapper
import com.example.tbank.domain.model.Trip

interface TripRepository {
    suspend fun getActiveTrips(): ResultWrapper<List<Trip>>
}