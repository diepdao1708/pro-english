package com.example.proenglish.data.models

import com.google.gson.annotations.SerializedName

data class LoginData(
    @SerializedName("accessToken")
    val accessToken: String,
)