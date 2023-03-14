package com.example.proenglish.features.exam.question

data class QuestionUiState(
    val isLoading: Boolean = false,
    val messageResId: Int? = null,
    val questions: List<QuestionItemUiState> = emptyList(),
    val score: Int = 0,
    val showCheckAnswer: Boolean = false,
)

data class QuestionItemUiState(
    val questionContent: String,
    val answer1: String,
    val answer2: String,
    val answer3: String,
    val answer4: String,
    val correctAnswer: String,
    val number: Int,
    var selectedAnswer: String? = null,
)