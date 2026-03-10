package com.payamanan.auctionfrontend.data.remote

import com.payamanan.auctionfrontend.data.model.Item
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ItemApi {

    @GET("api/item")
    suspend fun getItems() : List<Item>

    @POST("api/item")
    suspend fun createItem(@Body item: Item): Item



}