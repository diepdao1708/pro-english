package com.example.proenglish.domain.repositories

import com.example.proenglish.domain.models.ListPostData
import com.example.proenglish.domain.services.PostService
import retrofit2.Retrofit
import javax.inject.Inject

interface PostRepository {
    suspend fun getPosts(): Result<ListPostData>
}

class PostRepositoryImpl @Inject constructor(
    retrofit: Retrofit,
) : PostRepository {

    private val service = retrofit.create(PostService::class.java)

    override suspend fun getPosts(): Result<ListPostData> {
        return try {
            val response = service.getPosts()
            Result.success(response.data)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}