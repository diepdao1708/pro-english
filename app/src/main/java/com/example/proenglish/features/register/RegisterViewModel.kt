package com.example.proenglish.features.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proenglish.R
import com.example.proenglish.domain.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    sealed class Event {
        object NavigateToLogin : Event()
    }

    val email = MutableStateFlow("")
    val password = MutableStateFlow("")
    val confirmPassword = MutableStateFlow("")
    val fullname = MutableStateFlow("")

    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event>
        get() = _event

    private var _message = MutableLiveData<Int>()
    val message: LiveData<Int>
        get() = _message

    fun register() {
        viewModelScope.launch {
            authRepository.register(email.value, password.value, fullname.value)
                .onSuccess {
                    _message.postValue(R.string.register_success)
                    _event.postValue(Event.NavigateToLogin)
                    // TODO verify email
                }
                .onFailure {
                    _message.postValue(R.string.register_failure)
                }
        }
    }

    fun navigateToLogin() {
        _event.postValue(Event.NavigateToLogin)
    }
}