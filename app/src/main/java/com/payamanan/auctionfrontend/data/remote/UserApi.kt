package com.payamanan.auctionfrontend.data.remote

import com.payamanan.auctionfrontend.data.model.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApi {

    @POST("api/user")
    suspend fun signup(@Body user: User) : User

    @POST("api/login")
    suspend fun login(@Body user: User) : User

    @GET("api/user/{id}")
    suspend fun  get(@Path("id") id: Int) : User
}