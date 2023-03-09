package com.example.proenglish.features.home

import java.io.Serializable

data class PostItemUiState(
    val title: String,
    val description: String,
    val content: String,
    val createdAt: String,
) : Serializable
