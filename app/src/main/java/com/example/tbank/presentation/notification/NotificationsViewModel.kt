package com.example.tbank.presentation.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbank.data.model.ResultWrapper
import com.example.tbank.domain.model.NotificationModel
import com.example.tbank.domain.notification.GetNotificationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class NotificationsState {
    data object Loading: NotificationsState()
    data class Loaded(val list: List<NotificationModel>): NotificationsState()
}

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val getNotificationsUseCase: GetNotificationsUseCase
): ViewModel() {

    private val _state = MutableStateFlow<NotificationsState>(NotificationsState.Loading)
    val state: StateFlow<NotificationsState> = _state

    fun fetchNotifications(){
        _state.update {
            NotificationsState.Loading
        }
        viewModelScope.launch {
            when(val result = getNotificationsUseCase.invoke()){
                is ResultWrapper.Success -> _state.update { NotificationsState.Loaded(
                        result.value
                    )
                }
                else -> {}
            }
        }
    }
}