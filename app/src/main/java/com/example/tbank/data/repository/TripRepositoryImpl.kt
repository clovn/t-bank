package com.example.tbank.data.repository

import com.example.tbank.data.model.ResultWrapper
import com.example.tbank.data.model.TripRequest
import com.example.tbank.domain.model.TripInfo
import com.example.tbank.domain.model.User
import com.example.tbank.domain.repository.TripRepository
import javax.inject.Inject
import com.example.tbank.data.model.safeApiCall
import com.example.tbank.data.remote.TripApiService
import com.example.tbank.domain.model.Category
import kotlinx.coroutines.Dispatchers
import java.time.format.DateTimeFormatter
import javax.inject.Singleton

@Singleton
class TripRepositoryImpl @Inject constructor(
    private val tripApiService: TripApiService,
    private val expensesRepositoryImpl: ExpensesRepositoryImpl
): TripRepository {

    private var trip: TripRequest? = null

    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    override suspend fun saveTripInfo(tripInfo: TripInfo) {
        trip = TripRequest(
            tripInfo.name,
            formatter.format(tripInfo.startDate),
            formatter.format(tripInfo.endDate),
            tripInfo.budget,
            listOf()
        )
    }

    override suspend fun saveParticipants(participants: Set<User>) {
        trip = trip?.let {
            it.copy(
                participants = participants.map { user -> user.number }
            )
        }
    }

    override suspend fun saveTrip(categories: List<Category>): ResultWrapper<Unit> {
        return trip?.let {
            val result = safeApiCall(Dispatchers.IO) {
                tripApiService.createTrip(it)
            }

            when(result){
                is ResultWrapper.Success -> {

                    categories.forEach {
                        expensesRepositoryImpl.addCategory(it, result.value.id)
                    }

                    return ResultWrapper.Success(Unit)
                }

                is ResultWrapper.Error -> {
                    return ResultWrapper.Error(result.message)
                }
                is ResultWrapper.HttpError -> {
                    return ResultWrapper.Error(result.code.toString())
                }
            }
        } ?: ResultWrapper.Error(message = "Данные о поездки потеряны. Попробуйте снова")
    }

    override suspend fun getActiveTrip() = safeApiCall(Dispatchers.IO) {
        val list = tripApiService.getActiveTrips()
        if(list.isNotEmpty()){
            return@safeApiCall list[0]
        } else {
            return@safeApiCall null
        }
    }

    override fun getTripBudget() = trip?.totalBudget ?: 1000000
}