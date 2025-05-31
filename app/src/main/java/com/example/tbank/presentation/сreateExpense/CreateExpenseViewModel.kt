package com.example.tbank.presentation.сreateExpense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbank.domain.model.User
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class AddExpenseState{
    data class Idle(val list: List<User>): AddExpenseState()
    data object Loading: AddExpenseState()
    data object Success: AddExpenseState()
}

@AndroidEntryPoint
class CreateExpenseViewModel: ViewModel() {

    private val _uiState = MutableStateFlow<AddExpenseState>(AddExpenseState.Idle(fetchParticipants()))
    val uiState: StateFlow<AddExpenseState> = _uiState

    fun addExpense(){

    }

    fun fetchParticipants(): List<User> {
        viewModelScope.launch {
            runCatching {  }
        }

        return listOf()
    }
}