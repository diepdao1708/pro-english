package com.example.proenglish.features.splash

import android.content.ContentValues.TAG
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.proenglish.data.repositories.AuthRepositoryImpl.Companion.ACCESS_TOKEN
import com.example.proenglish.utils.JWTHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) : ViewModel() {

    companion object {
        const val DEFAULT_VALUE = "null"
    }

    sealed class Event {
        object NavigateToHome : Event()
        object NavigateToOnBoarding : Event()
    }

    private val _events = Channel<Event>(capacity = Channel.UNLIMITED)
    val events = _events.receiveAsFlow()

    fun checkLogin() {
        val accessToken = sharedPreferences.getString(ACCESS_TOKEN, DEFAULT_VALUE)
        Log.d(TAG, accessToken.toString())
        when {
            accessToken == DEFAULT_VALUE
                    || accessToken == null
                    || JWTHelper.expiredAccessToken(accessToken) -> _events.trySend(Event.NavigateToOnBoarding)
            else -> _events.trySend(Event.NavigateToHome)
        }
    }
}