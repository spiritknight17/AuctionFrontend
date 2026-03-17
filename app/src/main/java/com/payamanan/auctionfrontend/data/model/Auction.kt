package com.payamanan.auctionfrontend.data.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class Auction(
    val id: Int?,
    val item: Item,
    val startingPrice: Float,
    val currentBid: BidRequest?,
    val startTime: Date,
    val endTime: Date?,
    val status: String
){
    val hasValidBid: Boolean get() = currentBid != null
}