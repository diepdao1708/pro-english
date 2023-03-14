package com.example.proenglish.features.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proenglish.R
import com.example.proenglish.data.repositories.AuthRepository
import com.example.proenglish.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserUiState())
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()

    fun getCurrentUser() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            authRepository.getCurrentUser()
                .onSuccess { userData ->
                    _uiState.update {
                        it.copy(
                            fullname = userData.fullname,
                            email = userData.email,
                            score = userData.score.toString(),
                        )
                    }
                }
                .onFailure {
                    _uiState.update { it.copy(messageResId = R.string.user_failure) }
                }
            delay(500)
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun getLeaderboard() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            userRepository.getLeaderboard()
                .onSuccess { leaderboard ->
                    _uiState.update {
                        it.copy(
                            users = leaderboard.users.map { user ->
                                LeaderboardItemUiState(
                                    user.score.toString(),
                                    user.fullname,
                                )
                            },
                        )
                    }
                }
                .onFailure {
                    _uiState.update { it.copy(messageResId = R.string.leaderboard_failure) }
                }
            delay(500)
            _uiState.update { it.copy(isLoading = false) }
        }
    }
}