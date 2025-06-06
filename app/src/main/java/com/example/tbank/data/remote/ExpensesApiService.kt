package com.example.tbank.data.remote

import com.example.tbank.data.model.CategoryRequest
import com.example.tbank.data.model.ExpenseResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

//маппинги не окончательные

interface ExpensesApiService {
    @GET("trip/{tripId}/expenses/sum")
    suspend fun getTripExpensesSum(@Path("tripId") tripId: Long): Int

    @GET("trip/{tripId}/expenses?status=ACTUAL")
    suspend fun getTripExpenses(@Path("tripId") tripId: Long): List<ExpenseResponse>

    @POST("trip/{tripId}/expenses")
    suspend fun addCategory(@Path("tripId") tripId: Int, @Body categoryRequest: CategoryRequest)
}