package com.example.proenglish.data.models

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    private val email: String,
    private val password: String,
)

data class LoginGoogleRequest(
    @SerializedName("googleToken")
    private val googleToken: String,
)