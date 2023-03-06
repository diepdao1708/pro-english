package com.example.proenglish.domain.repositories

import com.example.proenglish.domain.models.ListQuestionData
import com.example.proenglish.domain.services.QuestionService
import retrofit2.Retrofit
import javax.inject.Inject

interface QuestionRepository {
    suspend fun getQuestions(level: String): Result<ListQuestionData>
}

class QuestionRepositoryImpl @Inject constructor(
    retrofit: Retrofit,
) : QuestionRepository {

    private val service = retrofit.create(QuestionService::class.java)

    override suspend fun getQuestions(level: String): Result<ListQuestionData> {
        return try {
            val response = service.getQuestions(level = level)
            Result.success(response.data)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}