package com.example.proenglish.features.onboarding

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor() : ViewModel() {

    sealed class Event {
        object NavigateToRegister : Event()
        object NavigateToLogin : Event()
    }

    private val _uiState = MutableStateFlow(OnBoardingUiState(onBoardings))
    val uiState: StateFlow<OnBoardingUiState> = _uiState.asStateFlow()

    private val _events = Channel<Event>(capacity = Channel.UNLIMITED)
    val events = _events.receiveAsFlow()
    fun navigateToRegister() {
        _events.trySend(Event.NavigateToRegister)
    }

    fun navigateToLogin() {
        _events.trySend(Event.NavigateToLogin)
    }
}