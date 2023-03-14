package com.example.proenglish.data.repositories

import com.example.proenglish.data.models.ListQuestionData
import com.example.proenglish.data.services.QuestionService
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