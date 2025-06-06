package com.example.tbank.presentation.createTrip

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbank.data.model.ResultWrapper
import com.example.tbank.domain.createTrip.SaveTripInfoUseCase
import com.example.tbank.domain.model.TripInfo
import com.example.tbank.domain.model.User
import com.example.tbank.domain.validation.ValidationManager
import com.example.tbank.presentation.model.FieldState
import com.example.tbank.presentation.register.RegisterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

data class TripInfoFormState(
    val tripNameState: FieldState = FieldState(),
    val tripBudgetState: FieldState = FieldState(),
    val startDate: LocalDate = LocalDate.now(),
    val endDate: LocalDate = LocalDate.now(),
    val isNextBtnActive: Boolean = false
)

@HiltViewModel
class CreateTripInfoViewModel @Inject constructor(
    private val validationManager: ValidationManager,
    private val saveTripInfoUseCase: SaveTripInfoUseCase
): ViewModel() {

    private val _formState = MutableStateFlow(TripInfoFormState())
    val formState: StateFlow<TripInfoFormState> = _formState

    fun saveInfo() {
        viewModelScope.launch {

            val currentState = _formState.value
            saveTripInfoUseCase.invoke(
                TripInfo(
                    startDate = currentState.startDate,
                    endDate = currentState.endDate,
                    name = currentState.tripNameState.value,
                    budget = currentState.tripBudgetState.value.toInt()
                )
            )
        }
    }

    fun onTripNameChanged(tripName: String) {
        updateFieldAndValidate {
            copy(tripNameState = FieldState(tripName, validationManager.isValidTripName(tripName)))
        }
    }

    fun onTripBudgetChanged(tripBudget: String) {
        updateFieldAndValidate {
            copy(tripBudgetState = FieldState(tripBudget, validationManager.isValidTripBudget(tripBudget)))
        }
    }

    fun setDates(startDate: LocalDate, endDate: LocalDate){
        _formState.update {
            it.copy(startDate = startDate, endDate = endDate)
        }
    }

    private fun updateFieldAndValidate(update: TripInfoFormState.() -> TripInfoFormState) {
        viewModelScope.launch {
            _formState.update{
                it.update()
            }
            validateFields()
        }
    }

    private fun validateFields() {
        val currentState = _formState.value
        val isNextBtnActive = currentState.tripNameState.isValid &&
                currentState.tripBudgetState.isValid

        _formState.update {
            it.copy(isNextBtnActive = isNextBtnActive)
        }
    }
}