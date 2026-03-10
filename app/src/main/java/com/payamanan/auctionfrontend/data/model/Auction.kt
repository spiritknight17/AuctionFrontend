package com.payamanan.auctionfrontend.data.model

import java.util.Date

data class Auction(
    val id: Int?,
    val item: Item,
    val startingPrice: Float,
    val currentBid: BidRequest?,
    val startTime: Date,
    val endTime: Date,
    val status: String
)
