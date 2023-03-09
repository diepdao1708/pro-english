package com.example.proenglish.features.user

data class UserUiState(
    val isLoading: Boolean = false,
    val messageResId: Int? = null,
    val fullname: String = "",
    val email: String = "",
    val score: String = "",
    val users: List<LeaderboardItemUiState> = emptyList(),
)