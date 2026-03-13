package com.payamanan.auctionfrontend.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.payamanan.auctionfrontend.data.ApiState
import com.payamanan.auctionfrontend.data.model.Item
import com.payamanan.auctionfrontend.data.remote.ItemApi
import com.payamanan.auctionfrontend.di.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ItemViewModel : ViewModel() {

    private val _items = MutableStateFlow<List<Item>>(emptyList());
    private val _itemState = MutableStateFlow<ApiState<Item>>(ApiState.Idle);

    val items: StateFlow<List<Item>> get() = _items
    val itemState: StateFlow<ApiState<Item>> get() = _itemState

    private val itemApi: ItemApi = RetrofitClient.auct_instance.create(ItemApi::class.java)

    fun createItem(item: Item) {
        viewModelScope.launch {
            _itemState.value = ApiState.Loading
            try {
                val response = itemApi.createItem(item)
                _itemState.value = ApiState.Success(response)
            }catch (e: Exception) {
                _itemState.value = ApiState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }

    fun getItems() {
        viewModelScope.launch {
            try {
                val response = itemApi.getItems()
                _items.value = response
            } catch (e: Exception) {
            }
        }
    }

    fun resetState() {
        _itemState.value = ApiState.Idle
    }
}