package com.example.tbank.data.remote

import com.example.tbank.domain.model.Trip
import retrofit2.http.GET

interface TripApiService {
    @GET("/trip?status=ACTIVE")
    suspend fun getActiveTrips(): List<Trip>
}