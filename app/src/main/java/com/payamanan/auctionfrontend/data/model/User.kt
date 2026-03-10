package com.payamanan.auctionfrontend.data.model

data class User(
    val userId: Int?,
    val username: String?,
    val email: String,
    val passwordHash: String,
    val role: String?,
    val status: String?
)
