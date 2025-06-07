package com.example.tbank.presentation.сreateExpense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbank.data.model.ResultWrapper
import com.example.tbank.domain.expenses.GetParticipantsUseCase
import com.example.tbank.domain.expenses.SaveExpenseUseCase
import com.example.tbank.domain.model.CategoryType
import com.example.tbank.domain.model.Expense
import com.example.tbank.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AddExpenseState{
    data object Idle: AddExpenseState()
    data object Loading: AddExpenseState()
    data object Success: AddExpenseState()
}

@HiltViewModel
class CreateExpenseViewModel @Inject constructor(
    private val getParticipantsUseCase: GetParticipantsUseCase,
    private val saveExpenseUseCase: SaveExpenseUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow<AddExpenseState>(AddExpenseState.Idle)
    val uiState: StateFlow<AddExpenseState> = _uiState

    private val _errorFlow = MutableSharedFlow<String>()
    val errorFlow:SharedFlow<String> = _errorFlow

    private var participants = listOf<User>()

    private val pickedParticipants = mutableSetOf<User>()

    fun addExpense(tripId: Int, type: CategoryType, description: String, amount: Int){
        _uiState.update {
            AddExpenseState.Loading
        }
        viewModelScope.launch {
            when(val result = saveExpenseUseCase.invoke(tripId, Expense(id = 0, type = type, name = description, amount = amount, authorName = "", debtors = pickedParticipants.toList()))){
                is ResultWrapper.Success -> {
                    _uiState.update {
                        AddExpenseState.Success
                    }
                }
                is ResultWrapper.Error -> {
                    _errorFlow.emit(result.message.toString())
                    _uiState.update {
                        AddExpenseState.Idle
                    }
                }
                is ResultWrapper.HttpError -> {
                    _errorFlow.emit("${result.message} - ${result.code}")
                    _uiState.update {
                        AddExpenseState.Idle
                    }
                }
            }
        }
    }

    fun fetchParticipants(tripId: Int) {
        viewModelScope.launch {
            when(val result = getParticipantsUseCase.invoke(tripId)){
                is ResultWrapper.Success -> {
                    participants = result.value
                }

                is ResultWrapper.Error -> {
                    _errorFlow.emit(result.message.toString())
                }
                is ResultWrapper.HttpError -> _errorFlow.emit("${result.message} - ${result.code}")
            }
        }
//        participants = listOf(
//            User(
//                id = 0,
//                firstName = "Ayrat",
//                lastName = "SOME",
//                number = "7999999999"
//            ),
//            User(
//                id = 1,
//                firstName = "John",
//                lastName = "Doe",
//                number = "82394324"
//            ),
//            User(
//                id = 2,
//                firstName = "Damin",
//                lastName = "awdawd",
//                number = "283742394"
//            )
//        )
    }

    fun getMatchedParticipants(query: String):List<User> {
        if(query.isEmpty()){
            return emptyList()
        } else {
            return participants.filter { user ->
                "${user.firstName} ${user.lastName}".lowercase().contains(query.lowercase())
            }
        }
    }

    fun addParticipant(user: User){
        pickedParticipants.add(user)
    }

    fun removeParticipant(user: User){
        pickedParticipants.add(user)
    }
}