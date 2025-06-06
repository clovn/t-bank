package com.example.tbank.data.remote

import com.example.tbank.data.model.TripRequest
import com.example.tbank.data.model.TripResponse
import com.example.tbank.domain.model.Trip
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TripApiService {
    @GET("trip?status=ACTIVE")
    suspend fun getActiveTrips(): List<Trip?>

    @POST("trip")
    suspend fun createTrip(@Body tripRequest: TripRequest): TripResponse
}