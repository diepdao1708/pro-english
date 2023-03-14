package com.example.proenglish.features.exam.question

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proenglish.R
import com.example.proenglish.data.models.Answer
import com.example.proenglish.data.repositories.QuestionRepository
import com.example.proenglish.data.repositories.UserRepository
import com.example.proenglish.features.exam.ExamViewModel.Companion.LEVEL_DATA
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor(
    private val questionRepository: QuestionRepository,
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(QuestionUiState())
    val uiState: StateFlow<QuestionUiState> = _uiState.asStateFlow()

    fun getQuestions(bundle: Bundle?) {
        val level = bundle?.get(LEVEL_DATA) as String
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            questionRepository.getQuestions(level)
                .onSuccess { listQuestionData ->
                    _uiState.update {
                        it.copy(questions = listQuestionData.questions.map { question ->
                            QuestionItemUiState(
                                questionContent = question.questionContent,
                                answer1 = question.answer1,
                                answer2 = question.answer2,
                                answer3 = question.answer3,
                                answer4 = question.answer4,
                                correctAnswer = question.correctAnswer,
                                number = question.number,
                            )
                        })
                    }
                }
                .onFailure {
                    _uiState.update { it.copy(messageResId = R.string.question_failure) }
                }
            delay(500)
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun selectAnswer(answer: Answer, position: Int) {
        _uiState.value.questions[position].selectedAnswer = answer.value
        val question = _uiState.value.questions.find { it.selectedAnswer == null }
        _uiState.update {
            it.copy(showCheckAnswer = question == null)
        }
    }

    fun checkAnswer() {
        var score = 0
        _uiState.value.questions.forEach { questionItemUiState ->
            if (questionItemUiState.correctAnswer == questionItemUiState.selectedAnswer) score += 1000
        }
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            userRepository.updateScore(score)
                .onSuccess { scoreData ->
                    _uiState.update {
                        it.copy(score = scoreData.score)
                    }
                }
                .onFailure {
                    _uiState.update { it.copy(messageResId = R.string.question_failure) }
                }
            delay(500)
            _uiState.update { it.copy(isLoading = false) }
        }
    }
}