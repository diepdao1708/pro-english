package com.example.starenglish.domain.services

import com.example.starenglish.domain.models.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthService {
    @POST("/api/v1/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): CommonResponse<LoginData>

    @GET("/api/v1/auth/")
    suspend fun getCurrentUser(): CommonResponse<UserData>

    @POST("/api/v1/auth/google")
    suspend fun loginWithGoogle(@Body loginGoogleRequest: LoginGoogleRequest): CommonResponse<LoginData>

    @POST("/api/v1/auth/register")
    suspend fun register(@Body registerRequest: RegisterRequest): RegisterData
}