package com.example.proenglish.data.models

import com.google.gson.annotations.SerializedName

data class RegisterData(
    @SerializedName("statusCode")
    val statusCode: Int,
    val message: String,
)