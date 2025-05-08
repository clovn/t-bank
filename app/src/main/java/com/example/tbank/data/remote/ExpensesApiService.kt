package com.example.tbank.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

//маппинги не окончательные

interface ExpensesApiService {
    @GET("trip/{tripId}/expenses/sum")
    suspend fun getTripExpensesSum(@Path("tripId") tripId: Long): Int
}