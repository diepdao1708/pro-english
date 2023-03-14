package com.example.proenglish.data.services

import com.example.proenglish.data.models.CommonResponse
import com.example.proenglish.data.models.ListQuestionData
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