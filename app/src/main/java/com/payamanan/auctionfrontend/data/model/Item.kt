package com.payamanan.auctionfrontend.data.model

data class Item(
    val id: Int?,
    val name: String,
    val description: String,
    val seller: User
)
