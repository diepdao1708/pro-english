package com.example.proenglish.domain.models

import com.google.gson.annotations.SerializedName

data class LeaderBoardData(
    val page: Int,
    val limit: Int,
    @SerializedName("totalPages")
    val totalPages: Int,
    val users: List<User>,
)

data class User(
    @SerializedName("_id")
    val id: String,
    val score: Int,
    val fullname: String,
)