package com.example.proenglish.data.models

data class RegisterRequest(
    val email: String,
    val password: String,
    val fullname: String,
)