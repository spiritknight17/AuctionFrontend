package com.payamanan.auctionfrontend.data.model

data class Transaction(
    val id: Int?,
    val auction: Auction,
    val buyer: User,
    val finalAmount: Float
)
