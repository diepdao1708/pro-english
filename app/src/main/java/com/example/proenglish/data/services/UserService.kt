package com.example.proenglish.data.services

import com.example.proenglish.data.models.CommonResponse
import com.example.proenglish.data.models.LeaderboardData
import com.example.proenglish.data.models.ScoreData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Query

interface UserService {

    @PATCH("/api/v1/users/update-score")
    suspend fun updateScore(
        @Body score: ScoreData
    ): CommonResponse<ScoreData>

    @GET("/api/v1/users/leaderboard")
    suspend fun getLeaderboard(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10,
    ): CommonResponse<LeaderboardData>
}