package com.payamanan.auctionfrontend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.payamanan.auctionfrontend.data.model.User
import com.payamanan.auctionfrontend.data.remote.UserApi
import com.payamanan.auctionfrontend.di.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel: ViewModel() {

    private val _user = MutableStateFlow<User?>(null);

    val user: StateFlow<User?> get() = _user

    private val userApi: UserApi = RetrofitClient.user_instance.create(UserApi::class.java)

    fun signup(user: User) {
        viewModelScope.launch {
            try {
                val response = userApi.signup(user)
                _user.value = response
            }catch (e: Exception) {
                print(e.message)
            }
        }
    }

    fun login(user: User) {
        viewModelScope.launch {
            try {
                val response = userApi.login(user)
                _user.value = response
            }catch (e: Exception) {
                print(e.message)
            }
        }
    }

    fun getDetails(id: Int) {
        viewModelScope.launch {
            try {
                val response = userApi.get(id)
                _user.value = response
            }catch (e: Exception) {
                print(e.message)
            }
        }
    }

}