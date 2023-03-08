package com.example.proenglish.features.home

data class HomeUiState(
    val isLoading: Boolean = false,
    val messageResId: Int? = null,
    val posts: List<PostItemUiState> = emptyList(),
)