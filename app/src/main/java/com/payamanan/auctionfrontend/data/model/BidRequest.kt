package com.payamanan.auctionfrontend.data.model

data class BidRequest(
    val id: Int?,
    val userId: Int,
    val offeredPrice: Float
)
