package com.example.proenglish.domain.services

import com.example.proenglish.domain.models.DictionaryData
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryService {

    @GET("/api/v1/dictionary/{word}")
    suspend fun takeMeaning(
        @Path("word") word: String
    ): List<DictionaryData>
}