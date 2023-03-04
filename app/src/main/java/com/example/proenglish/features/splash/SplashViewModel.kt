package com.example.proenglish.features.splash

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proenglish.domain.repositories.AuthRepositoryImpl.Companion.ACCESS_TOKEN
import com.example.proenglish.utils.JWTHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) : ViewModel() {

    companion object {
        const val DEFAULT_VALUE = "null"
    }

    sealed class Event {
        object NavigateToLogin : Event()
        object NavigateToHome : Event()
    }

    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event> = _event

    fun checkLogin() {
        val accessToken = sharedPreferences.getString(ACCESS_TOKEN, DEFAULT_VALUE)
        Log.d("TAG", accessToken.toString())
        when {
            accessToken == DEFAULT_VALUE
                    || accessToken == null
                    || JWTHelper.expiredAccessToken(accessToken) -> _event.postValue(Event.NavigateToLogin)
            else -> _event.postValue(Event.NavigateToHome)
        }
    }
}