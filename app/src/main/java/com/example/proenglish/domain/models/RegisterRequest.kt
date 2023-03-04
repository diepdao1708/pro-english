package com.example.proenglish.domain.models

data class RegisterRequest(
    val email: String,
    val password: String,
    val fullname: String,
)