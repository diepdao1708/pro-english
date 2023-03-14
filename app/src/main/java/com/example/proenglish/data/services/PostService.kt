package com.example.proenglish.data.services

import com.example.proenglish.data.models.CommonResponse
import com.example.proenglish.data.models.ListPostData
import retrofit2.http.GET
import retrofit2.http.Query

interface PostService {

    @GET("/api/v1/posts")
    suspend fun getPosts(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 100,
    ): CommonResponse<ListPostData>
}