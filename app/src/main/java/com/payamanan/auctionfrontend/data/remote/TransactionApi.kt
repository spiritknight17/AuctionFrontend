package com.payamanan.auctionfrontend.data.remote

import com.payamanan.auctionfrontend.data.model.Transaction
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TransactionApi {

    @GET("api/transaction/{userId}")
    suspend fun getTransactions(@Path("userId") userId: Int): List<Transaction>

    @POST("api/transaction")
    suspend fun createTransaction(@Body transaction: Transaction): Transaction
}