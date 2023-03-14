package com.example.proenglish.domain.models

import com.google.gson.annotations.SerializedName

data class ListQuestionData(
    val page: Int,
    val limit: Int,
    val count: Int,
    val questions: List<QuestionData>,
)

data class QuestionData(
    @SerializedName("_id")
    val id: String,
    val questionContent: String,
    val level: Level,
    val answer1: String,
    val answer2: String,
    val answer3: String,
    val answer4: String,
    val correctAnswer: String,
    val number: Int,
)

enum class Level(val value: String) {
    EASY("easy"),
    MEDIUM("medium"),
    HARD("hard"),
}

enum class Answer(val value: String) {
    ANSWER1("answer1"),
    ANSWER2("answer2"),
    ANSWER3("answer3"),
    ANSWER4("answer4"),
}