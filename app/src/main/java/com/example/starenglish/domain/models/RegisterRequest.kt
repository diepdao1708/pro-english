package com.example.starenglish.domain.models

data class RegisterRequest(
    val email: String,
    val password: String,
    val fullname: String,
)