package com.example.proenglish.domain.repositories

import com.example.proenglish.domain.models.LeaderBoardData
import com.example.proenglish.domain.models.ScoreData
import com.example.proenglish.domain.services.UserService
import retrofit2.Retrofit
import javax.inject.Inject

interface UserRepository {
    suspend fun updateScore(score: Int): Result<ScoreData>
    suspend fun getLeaderBoard(): Result<LeaderBoardData>
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

    override suspend fun getLeaderBoard(): Result<LeaderBoardData> {
        return try {
            val response = service.getLeaderBoard()
            Result.success(response.data)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}