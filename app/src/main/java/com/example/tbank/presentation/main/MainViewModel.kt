package com.example.tbank.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbank.data.model.ResultWrapper
import com.example.tbank.domain.main.GetTripExpensesSumUseCase
import com.example.tbank.domain.main.GetTripInfoUseCase
import com.example.tbank.domain.main.GetUserInfoUseCase
import com.example.tbank.domain.model.Trip
import com.example.tbank.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class UiState {
    data object Loading: UiState()
    data class Loaded(
        var user: User? = null,
        var trip: Trip? = null,
        var expensesSum: Int? = null
    ): UiState()
    data class Error(val message: String): UiState()
}


@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTripInfoUseCase: GetTripInfoUseCase,
    private val getTripExpensesSumUseCase: GetTripExpensesSumUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    fun fetchData() {
        viewModelScope.launch {
            _uiState.update { UiState.Loading }

            val state = UiState.Loaded()

            when(val userState = getUserInfoUseCase.invoke()) {
                is ResultWrapper.Success -> {
                    state.user = userState.value
                }
                is ResultWrapper.Error -> {
                    _uiState.update { UiState.Error(userState.message ?: "Неизвестная ошибка") }
                    return@launch
                }
            }

            when(val tripState = getTripInfoUseCase.invoke()) {
                is ResultWrapper.Success -> {
                    state.trip = tripState.value
                }
                is ResultWrapper.Error -> {
                    _uiState.update {
                        UiState.Error(tripState.message ?: "Неизвестная ошибка")
                        return@launch
                    }
                }
            }

            state.trip?.let {
                when(val expensesSumState = getTripExpensesSumUseCase.invoke(it.id)){
                    is ResultWrapper.Success -> {
                        state.expensesSum = expensesSumState.value
                    }
                    is ResultWrapper.Error -> {
                        UiState.Error(expensesSumState.message ?: "Неизвестная ошибка")
                        return@launch
                    }
                }
            }

            _uiState.update { state }
        }

    }
}