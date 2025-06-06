package com.example.tbank.presentation.createTrip

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbank.data.model.ResultWrapper
import com.example.tbank.domain.createTrip.GetUsersUseCase
import com.example.tbank.domain.createTrip.SaveTripParticipantsUseCase
import com.example.tbank.domain.model.User
import com.example.tbank.presentation.formatPhoneNumber
import com.example.tbank.presentation.normalizePhoneNumber
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class Participants(
    val query: String,
    val participants: List<User>
)

@HiltViewModel
class CreateTripParticipantsViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
    private val saveTripParticipantsUseCase: SaveTripParticipantsUseCase
): ViewModel() {

    private val _state = MutableStateFlow(Participants("", listOf()))
    val state: StateFlow<Participants> = _state

    private val _errorFlow = MutableSharedFlow<String>()
    val errorFlow: SharedFlow<String> = _errorFlow

    private val participants = mutableSetOf<User>()

    fun renderQuery(query: String){
        val number = formatPhoneNumber(query.replace(Regex("\\D"), ""))

        if(number.length < 16){

        } else {
            viewModelScope.launch {
                when(val result = getUsersUseCase.invoke(normalizePhoneNumber(number))){
                    is ResultWrapper.Success -> {
                        _state.update {
                            Participants(number, result.value)
                        }
                    }
                    is ResultWrapper.Error -> {
                        _errorFlow.emit(result.message.toString())
                    }
                    is ResultWrapper.HttpError -> {
                        _errorFlow.emit("${result.message}(${result.code})")
                    }
                }
            }
        }
    }

    fun saveInfo() {
        viewModelScope.launch {
            saveTripParticipantsUseCase.invoke(participants)
        }
    }

    fun addParticipant(user: User){
        participants.add(user)
    }

    fun removeParticipant(user: User){
        participants.add(user)
    }
}