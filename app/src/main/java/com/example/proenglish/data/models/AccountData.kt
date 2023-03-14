package com.example.proenglish.data.models

import com.example.proenglish.utils.JWTHelper


data class SavedAccount(var accessToken: String? = null) {
    val data: AccountData?
        get() = JWTHelper.decode(accessToken)
}

data class AccountData(
    val userId: String,
    val fullname: String,
    val email: String,
    val role: String,
    val iat: Long,
    val exp: Long,
)