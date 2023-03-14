package com.example.proenglish.domain.services

import com.example.proenglish.domain.models.CommonResponse
import com.example.proenglish.domain.models.ListQuestionData
import retrofit2.http.GET
import retrofit2.http.Query

interface QuestionService {

    @GET("/api/v1/questions")
    suspend fun getQuestions(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10,
        @Query("level") level: String,
    ): CommonResponse<ListQuestionData>
}