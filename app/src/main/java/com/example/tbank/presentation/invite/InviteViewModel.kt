package com.example.tbank.presentation.invite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbank.data.model.ResultWrapper
import com.example.tbank.domain.invite.InviteAcceptOrRejectUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class InviteState{
    data object Idle: InviteState()
    data object Loading: InviteState()
    data object Success: InviteState()
}

@HiltViewModel
class InviteViewModel @Inject constructor(
    private val inviteAcceptOrRejectUseCase: InviteAcceptOrRejectUseCase
): ViewModel() {

    private val _state = MutableStateFlow<InviteState>(InviteState.Idle)
    val state: StateFlow<InviteState> = _state

    private val _errorFlow = MutableSharedFlow<String>()
    val error:SharedFlow<String> = _errorFlow


    fun invite(tripId: Int, isAccept: Boolean){
        _state.update {
            InviteState.Loading
        }
        viewModelScope.launch {
            when(val result = inviteAcceptOrRejectUseCase.invoke(tripId, isAccept)){
                is ResultWrapper.Success -> _state.update { InviteState.Success }
                is ResultWrapper.Error -> {
                    _errorFlow.emit("${result.message}")
                    _state.update { InviteState.Idle }
                }
                is ResultWrapper.HttpError -> {
                    _errorFlow.emit("${result.message}")
                    _state.update { InviteState.Idle }
                }
            }
        }
    }
}