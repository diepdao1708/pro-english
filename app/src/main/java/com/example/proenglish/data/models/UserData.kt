package com.example.proenglish.data.models

import com.google.gson.annotations.SerializedName

data class UserData(
    val role: String,
    val score: Long,
    @SerializedName("_id")
    val id: String,
    val email: String,
    val fullname: String,
)