package com.example.tbank.presentation.createTrip

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbank.domain.validation.ValidationManager
import com.example.tbank.presentation.model.FieldState
import com.example.tbank.presentation.model.RegisterFormState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TripInfoFormState(
    val tripNameState: FieldState = FieldState(),
    val tripBudgetState: FieldState = FieldState(),
    val isNextBtnActive: Boolean = false
)

@HiltViewModel
class CreateTripInfoViewModel @Inject constructor(
    private val validationManager: ValidationManager
): ViewModel() {

    private val _formState = MutableStateFlow(TripInfoFormState())
    val formState: StateFlow<TripInfoFormState> = _formState

    fun saveInfo() {

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