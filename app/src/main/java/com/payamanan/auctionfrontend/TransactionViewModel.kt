package com.payamanan.auctionfrontend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.payamanan.auctionfrontend.data.model.Auction
import com.payamanan.auctionfrontend.data.model.Transaction
import com.payamanan.auctionfrontend.data.model.User
import com.payamanan.auctionfrontend.data.remote.TransactionApi
import com.payamanan.auctionfrontend.data.remote.UserApi
import com.payamanan.auctionfrontend.di.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TransactionViewModel: ViewModel() {

    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList());

    val transactions: StateFlow<List<Transaction>> get() = _transactions

    private val transactionApi: TransactionApi = RetrofitClient.user_instance.create(TransactionApi::class.java)

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
}