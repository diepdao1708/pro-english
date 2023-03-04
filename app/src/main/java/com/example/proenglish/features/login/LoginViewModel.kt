package com.example.proenglish.features.login

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proenglish.R
import com.example.proenglish.domain.models.LoginData
import com.example.proenglish.domain.repositories.AuthRepository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val googleSignInClient: GoogleSignInClient,
    private val authRepository: AuthRepository,
) : ViewModel() {

    sealed class Event {
        object NavigateToHome : Event()
        object NavigateToRegister : Event()
    }

    val email = MutableStateFlow("")
    val password = MutableStateFlow("")

    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event>
        get() = _event

    private val _signInIntent = MutableLiveData<Intent>()
    val signInIntent: LiveData<Intent>
        get() = _signInIntent

    private val _googleToken = MutableLiveData<String>()
    val googleToken: LiveData<String>
        get() = _googleToken

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private var _error = MutableLiveData<Int>()
    val error: LiveData<Int>
        get() = _error

    fun login() {
        viewModelScope.launch {
            authRepository.login(email.value, password.value)
                .onSuccess { loginData ->
                    saveAccount(loginData)
                }
                .onFailure {
                    onError(R.string.log_in_failure)
                }
        }
    }

    fun signInWithGoogle() {
        _isLoading.postValue(true)
        googleSignInClient.signOut()
        _signInIntent.postValue(googleSignInClient.signInIntent)
    }

    fun navigateToRegister() {
        _event.postValue(Event.NavigateToRegister)
    }

    fun handleLoginResult(data: Intent?) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)
            account.idToken?.let { token ->
                loginWithGoogle(token)
            } ?: run {
                onError(R.string.can_not_get_account)
            }
        } catch (e: ApiException) {
            when (e.statusCode) {
                GoogleSignInStatusCodes.NETWORK_ERROR -> onError(R.string.check_network)
                GoogleSignInStatusCodes.SIGN_IN_CANCELLED -> _isLoading.postValue(false)
                else -> onError(R.string.try_again)
            }
        }
    }

    private fun loginWithGoogle(googleToken: String) {
        viewModelScope.launch {
            authRepository.loginWithGoogle(googleToken)
                .onSuccess { loginData ->
                    saveAccount(loginData)
                }
                .onFailure {
                    onError(R.string.log_in_failure)
                }
        }
    }

    private fun saveAccount(loginData: LoginData) {
        authRepository.saveAccount(loginData.accessToken)
            .onSuccess {
                _event.postValue(Event.NavigateToHome)
            }
            .onFailure {
                // TODO
            }
    }

    private fun onError(message: Int) {
        _isLoading.postValue(false)
        _error.postValue(message)
    }
}