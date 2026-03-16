package com.payamanan.auctionfrontend.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.payamanan.auctionfrontend.data.UserSesssion.user
import com.payamanan.auctionfrontend.data.model.Auction
import com.payamanan.auctionfrontend.data.model.Transaction
import com.payamanan.auctionfrontend.data.model.User
import com.payamanan.auctionfrontend.data.remote.AuctionApi
import com.payamanan.auctionfrontend.data.remote.TransactionApi
import com.payamanan.auctionfrontend.data.remote.UserApi
import com.payamanan.auctionfrontend.di.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TransactionViewModel: ViewModel() {
    val currentUser = user
    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList());

    val transactions: StateFlow<List<Transaction>> get() = _transactions
    private val userApi: UserApi = RetrofitClient.user_instance.create(UserApi::class.java)
    private val transactionApi: TransactionApi = RetrofitClient.auct_instance.create(TransactionApi::class.java)
    private val auctionApi: AuctionApi = RetrofitClient.auct_instance.create(AuctionApi::class.java)

    fun createAuction(transaction: Transaction) {
        viewModelScope.launch {
            try {
                transactionApi.createTransaction(transaction)
            }catch (e: Exception) {
                print(e.message)
            }
        }
    }

    fun getAuctions(userId: Int) {
        viewModelScope.launch {
            try {
                val response = transactionApi.getTransactions(userId)
                _transactions.value = response
            }catch (e: Exception) {
                print(e.message)
            }
        }
    }
    fun finalizeAuction(auction: Auction) {
        val highestBid = auction.currentBid
        val buyer = currentUser

        if (highestBid != null && buyer != null) {
            viewModelScope.launch {
                try {
                    val newTransaction = Transaction(
                        id = null,
                        auction = auction,
                        buyer = buyer,
                        finalAmount = highestBid.offeredPrice
                    )

                    // POST to API
                    transactionApi.createTransaction(newTransaction)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}
