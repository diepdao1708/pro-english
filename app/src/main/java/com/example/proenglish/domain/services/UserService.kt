package com.example.proenglish.domain.services

import com.example.proenglish.domain.models.CommonResponse
import com.example.proenglish.domain.models.LeaderBoardData
import com.example.proenglish.domain.models.ScoreData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Query

interface UserService {

    @PATCH("v1/users/update-score")
    suspend fun updateScore(
        @Body score: ScoreData
    ): CommonResponse<ScoreData>

    @GET("v1/users/leaderboard")
    suspend fun getLeaderBoard(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10,
    ): CommonResponse<LeaderBoardData>
}