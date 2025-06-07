package com.example.tbank.data.remote

import com.example.tbank.data.model.CategoryRequest
import com.example.tbank.data.model.ExpenseRequest
import com.example.tbank.data.model.ExpensesResponse
import com.example.tbank.data.model.ExpensesSum
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ExpensesApiService {
    @GET("trip/{tripId}/expenses/sum?status=ACTUAL")
    suspend fun getTripExpensesSum(@Path("tripId") tripId: Long): List<ExpensesSum>

    @GET("trip/{tripId}/expenses?status=ACTUAL")
    suspend fun getTripExpenses(@Path("tripId") tripId: Long): ExpensesResponse

    @POST("trip/{tripId}/expenses")
    suspend fun saveExpense(@Path("tripId") tripId: Int, @Body categoryRequest: ExpenseRequest)

    @POST("trip/{tripId}/expenses")
    suspend fun addCategory(@Path("tripId") tripId: Int, @Body categoryRequest: CategoryRequest)
}