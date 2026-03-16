package com.payamanan.auctionfrontend.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.payamanan.auctionfrontend.data.model.Auction
import com.payamanan.auctionfrontend.data.model.BidRequest
import com.payamanan.auctionfrontend.data.model.Item
import com.payamanan.auctionfrontend.data.remote.AuctionApi
import com.payamanan.auctionfrontend.di.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuctionViewModel: ViewModel() {
    private val _auctions = MutableStateFlow<List<Auction>>(emptyList());

    val auctions: StateFlow<List<Auction>> get() = _auctions

    private val auctionApi: AuctionApi = RetrofitClient.auct_instance.create(AuctionApi::class.java)

    init {
        getAuctions();
    }

    fun createAuction(auction: Auction) {
        viewModelScope.launch {
            try {
                auctionApi.createAuction(auction)
                getAuctions()
            }catch (e: Exception) {
                print(e.message)
            }
        }
    }

    fun getAuctions() {
        viewModelScope.launch {
            try {
                val response = auctionApi.getAuctions()
                _auctions.value = response
            }catch (e: Exception) {
                print(e.message)
            }
        }
    }
    fun bid(auctionId:Int, bidRequest: BidRequest) {
        viewModelScope.launch {
            try {
                auctionApi.bidAuction(auctionId, bidRequest)
                getAuctions()
            }catch (e: Exception) {
                print(e.message)
            }
        }
    }
    fun startAuctionProcess(item: Item, startingPrice: Float) {
        viewModelScope.launch {
            try {
                val initialBid = BidRequest(
                    id = null,
                    userId = item.seller.userId!!,
                    offeredPrice = startingPrice
                )
                val newAuction = Auction(
                    id = null,
                    item = item,
                    startingPrice = startingPrice,
                    currentBid = initialBid,
                    startTime = java.util.Date(),
                    endTime = java.util.Date(System.currentTimeMillis() + 86400000),
                    status = "Pending"
                )
                auctionApi.createAuction(newAuction)
                getAuctions()
            } catch (e: Exception) {
                Log.e("AuctionViewModel", "Failed to start auction: ${e.message}")
            }
        }
    }
}