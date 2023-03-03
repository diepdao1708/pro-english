package com.example.starenglish.features.main

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.starenglish.R
import com.example.starenglish.domain.repositories.AuthRepositoryImpl.Companion.ACCESS_TOKEN
import com.example.starenglish.features.splash.SplashViewModel.Companion.DEFAULT_VALUE
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val googleSignInClient: GoogleSignInClient,
    private val sharedPreferences: SharedPreferences,
) : ViewModel() {

    sealed class Event {
        object LogOut : Event()
    }

    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event>
        get() = _event

    private val _message = MutableLiveData<Int>()
    val message: LiveData<Int>
        get() = _message

    fun logOut() {
        sharedPreferences.edit {
            putString(ACCESS_TOKEN, DEFAULT_VALUE).apply()
        }
        googleSignInClient.signOut()
        _event.postValue(Event.LogOut)
        _message.postValue(R.string.log_out_success)
    }
}