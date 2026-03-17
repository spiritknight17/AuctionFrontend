package com.payamanan.auctionfrontend.data.remote

import com.payamanan.auctionfrontend.data.model.Item
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ItemApi {

    @GET("api/item")
    suspend fun getItems() : List<Item>

    @POST("api/item")
    suspend fun createItem(@Body item: Item): Item

    @PUT("api/item/{id}")
    suspend fun updateItem(@Path("id") id: Int, @Body item: Item): Item

}