package com.payamanan.auctionfrontend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.payamanan.auctionfrontend.data.model.Item
import com.payamanan.auctionfrontend.data.model.User
import com.payamanan.auctionfrontend.data.remote.ItemApi
import com.payamanan.auctionfrontend.data.remote.UserApi
import com.payamanan.auctionfrontend.di.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ItemViewModel : ViewModel() {

    private val _items = MutableStateFlow<List<Item>>(emptyList());

    val items: StateFlow<List<Item>> get() = _items

    private val itemApi: ItemApi = RetrofitClient.auct_instance.create(ItemApi::class.java)

    fun createItem(item: Item) {
        viewModelScope.launch {
            try {
                itemApi.createItem(item)
            }catch (e: Exception) {
                print(e.message)
            }
        }
    }

    fun getItems() {
        viewModelScope.launch {
            try {
                val response = itemApi.getItems()
                _items.value = response
            }catch (e: Exception) {
                print(e.message)
            }
        }
    }
}