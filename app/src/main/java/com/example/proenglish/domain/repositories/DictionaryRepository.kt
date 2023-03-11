package com.example.proenglish.domain.repositories

import com.example.proenglish.domain.models.DictionaryData
import com.example.proenglish.domain.services.DictionaryService
import retrofit2.Retrofit
import javax.inject.Inject

interface DictionaryRepository {
    suspend fun takeMeaning(word: String): Result<List<DictionaryData>>
}

class DictionaryRepositoryImpl @Inject constructor(
    retrofit: Retrofit,
) : DictionaryRepository {

    private val service = retrofit.create(DictionaryService::class.java)

    override suspend fun takeMeaning(word: String): Result<List<DictionaryData>> {
        return try {
            val response = service.takeMeaning(word)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}