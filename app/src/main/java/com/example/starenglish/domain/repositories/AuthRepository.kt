package com.example.starenglish.domain.repositories

import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import com.example.starenglish.domain.models.*
import com.example.starenglish.domain.services.AuthService
import retrofit2.Retrofit
import javax.inject.Inject

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<LoginData>
    suspend fun getCurrentUser(): Result<UserData>
    suspend fun loginWithGoogle(googleToken: String): Result<LoginData>
    suspend fun register(email: String, password: String, fullName: String): Result<RegisterData>
    fun saveAccount(accessToken: String): Result<Unit>
}

class AuthRepositoryImpl @Inject constructor(
    retrofit: Retrofit,
    private val savedAccount: SavedAccount,
    private val sharedPreferences: SharedPreferences,
) : AuthRepository {

    companion object {
        const val ACCESS_TOKEN = "ACCESS_TOKEN"
    }

    private val service = retrofit.create(AuthService::class.java)

    override suspend fun login(email: String, password: String): Result<LoginData> {
        return try {
            val loginRequest = LoginRequest(email, password)
            val loginResponse = service.login(loginRequest)
            Result.success(loginResponse.data)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCurrentUser(): Result<UserData> {
        return try {
            val userResponse = service.getCurrentUser()
            Result.success(userResponse.data)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun loginWithGoogle(googleToken: String): Result<LoginData> {
        return try {
            val loginRequest = LoginGoogleRequest(googleToken)
            Log.d("TAG", googleToken)
            val loginResponse = service.loginWithGoogle(loginRequest)
            Result.success(loginResponse.data)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun register(
        email: String,
        password: String,
        fullName: String
    ): Result<RegisterData> {
        return try {
            val registerRequest = RegisterRequest(email, password, fullName)
            val registerResponse = service.register(registerRequest)
            Result.success(registerResponse)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun saveAccount(accessToken: String): Result<Unit> {
        return try {
            sharedPreferences.edit {
                putString(ACCESS_TOKEN, accessToken).apply()
            }
            savedAccount.accessToken = accessToken
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}