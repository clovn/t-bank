package com.example.tbank.presentation.createTrip

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbank.data.model.ResultWrapper
import com.example.tbank.domain.createTrip.GetTripBudgetUseCase
import com.example.tbank.domain.createTrip.SaveTripExpensesUseCase
import com.example.tbank.domain.model.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class State {
    data object Idle: State()
    data object Loading: State()
    data object Loaded: State()
}


@HiltViewModel
class CreateTripExpensesViewModel @Inject constructor(
    private val saveTripExpensesUseCase: SaveTripExpensesUseCase,
    private val getTripBudgetUseCase: GetTripBudgetUseCase
): ViewModel(){

    private val _state = MutableStateFlow<State>(State.Idle)
    val state: StateFlow<State> = _state

    private val _errorFlow = MutableSharedFlow<String>()
    val errorFlow: SharedFlow<String> = _errorFlow

    private val categories = mutableListOf<Category>()

    fun save(){
        _state.update {
            State.Loading
        }
        viewModelScope.launch {
            runCatching {
                when(val result = saveTripExpensesUseCase.invoke(categories)){
                    is ResultWrapper.Success -> {
                        _state.update {
                            State.Loaded
                        }
                    }
                    is ResultWrapper.Error -> {
                        _errorFlow.emit(result.message.toString())
                        _state.update {
                            State.Idle
                        }
                    }
                    is ResultWrapper.HttpError -> {
                        _errorFlow.emit(result.code.toString())
                        _state.update {
                            State.Idle
                        }
                    }
                }
            }
        }
    }

    fun addCategory(category: Category){
        categories.add(category)
    }

    fun removeCategory(category: Category){
        categories.remove(category)
    }


    fun getBudget() = getTripBudgetUseCase.invoke()

    fun getRemainingPercentage() = 100 - categories.sumOf { it.percent }
}