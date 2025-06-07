package com.example.tbank.data.remote

import com.example.tbank.data.model.TripRequest
import com.example.tbank.data.model.TripIdResponse
import com.example.tbank.data.model.TripResponse
import com.example.tbank.domain.model.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TripApiService {
    @GET("trip?status=ACTIVE")
    suspend fun getActiveTrips(): List<TripResponse?>

    @POST("trip")
    suspend fun createTrip(@Body tripRequest: TripRequest): TripIdResponse

    @GET("trip/{tripId}/participants")
    suspend fun getParticipants(@Path("tripId") tripId: Int): List<User>
}