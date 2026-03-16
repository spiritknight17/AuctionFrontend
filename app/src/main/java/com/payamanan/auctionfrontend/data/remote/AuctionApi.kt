package com.payamanan.auctionfrontend.data.remote

import com.payamanan.auctionfrontend.data.model.Auction
import com.payamanan.auctionfrontend.data.model.BidRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AuctionApi {

    @GET("api/auction")
    suspend fun getAuctions(): List<Auction>

    @POST("api/auction")
    suspend fun createAuction(@Body auction: Auction): Auction

    @POST("api/auction/{id}/bid")
    suspend fun bidAuction(@Path("id") id: Int, @Body bidRequest: BidRequest): String

    @GET("api/auction/{id}")
    suspend fun getAuction(@Path("id") id: Int): Auction
}