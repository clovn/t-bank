package com.example.tbank.presentation.expenses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbank.data.model.ResultWrapper
import com.example.tbank.domain.expenses.GetExpensesUseCase
import com.example.tbank.domain.expenses.mapCategoryExpensesUseCase
import com.example.tbank.domain.model.Category
import com.example.tbank.domain.model.Expense
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ExpensesState{
    data object Loading: ExpensesState()
    data class Loaded(
        val expensesList: List<Expense>,
        val categoriesList: List<Category>
    ): ExpensesState()
    data class Error(val message: String): ExpensesState()
}

@HiltViewModel
class ExpensesViewModel @Inject constructor(
    private val getExpensesUseCase: GetExpensesUseCase,
    private val mapCategoryExpensesUseCase: mapCategoryExpensesUseCase
): ViewModel() {

    private val _expensesState = MutableStateFlow<ExpensesState>(ExpensesState.Loading)
    val expensesState: StateFlow<ExpensesState> = _expensesState

    fun fetchData(tripId: Long){
        viewModelScope.launch {
            _expensesState.update { ExpensesState.Loading }

            when(val data = getExpensesUseCase.invoke(tripId)){
                is ResultWrapper.Success -> {
                    _expensesState.update {
                        ExpensesState.Loaded(
                            expensesList = data.value,
                            categoriesList = mapCategoryExpensesUseCase.invoke(data.value)
                        )
                    }
                }
                is ResultWrapper.Error -> {
                    _expensesState.update {
                        ExpensesState.Error(message = data.message ?: "Неизвестная ошибка")
                    }
                }
                is ResultWrapper.HttpError -> {
                    _expensesState.update {
                        ExpensesState.Error(message = "${data.message} (${data.code})")
                    }
                }
            }
        }
    }
}