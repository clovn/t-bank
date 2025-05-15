package com.example.tbank.data.repository.mock

import com.example.tbank.data.model.ResultWrapper
import com.example.tbank.domain.repository.ExpensesRepository
import javax.inject.Inject

class ExpensesRepositoryMockImpl @Inject constructor(): ExpensesRepository {
    override suspend fun getTripExpensesSum(tripId: Long) = ResultWrapper.Success(875800)
}