package com.example.proenglish.domain.repositories

import com.example.proenglish.domain.models.LeaderboardData
import com.example.proenglish.domain.models.ScoreData
import com.example.proenglish.domain.services.UserService
import retrofit2.Retrofit
import javax.inject.Inject

interface UserRepository {
    suspend fun updateScore(score: Int): Result<ScoreData>
    suspend fun getLeaderboard(): Result<LeaderboardData>
}

class UserRepositoryImpl @Inject constructor(
    retrofit: Retrofit,
) : UserRepository {

    private val service = retrofit.create(UserService::class.java)

    override suspend fun updateScore(score: Int): Result<ScoreData> {
        return try {
            val scoreData = ScoreData(score)
            val response = service.updateScore(scoreData)
            Result.success(response.data)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getLeaderboard(): Result<LeaderboardData> {
        return try {
            val response = service.getLeaderboard()
            Result.success(response.data)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}