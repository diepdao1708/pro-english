package com.example.proenglish.domain.models

import com.google.gson.annotations.SerializedName
import java.util.Date

data class ListPostData(
    val page: Int,
    val limit: Int,
    val posts: List<PostData>,
)

data class PostData(
    @SerializedName("_id")
    val id: String,
    val title: String,
    val description: String,
    val content: String,
    @SerializedName("createAt")
    val createdAt: Date,
)