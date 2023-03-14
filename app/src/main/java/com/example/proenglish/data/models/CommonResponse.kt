package com.example.proenglish.data.models

import com.google.gson.annotations.SerializedName

data class CommonResponse<D>(
    @SerializedName("statusCode")
    val statusCode: Int,
    val message: String,
    val data: D
)