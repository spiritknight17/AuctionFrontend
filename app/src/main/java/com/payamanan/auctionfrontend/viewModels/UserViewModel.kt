package com.payamanan.auctionfrontend.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.payamanan.auctionfrontend.data.ApiState
import com.payamanan.auctionfrontend.data.UserSesssion
import com.payamanan.auctionfrontend.data.model.User
import com.payamanan.auctionfrontend.data.remote.UserApi
import com.payamanan.auctionfrontend.di.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel: ViewModel() {

    private val _userState = MutableStateFlow<ApiState<User>>(ApiState.Idle);
    val userState: StateFlow<ApiState<User>> =  _userState

    private val userApi: UserApi = RetrofitClient.user_instance.create(UserApi::class.java)

    fun signup(user: User) {
        viewModelScope.launch {
            _userState.value = ApiState.Loading
            try {
                val response = userApi.signup(user)
                _userState.value = ApiState.Success(response)
            }catch (e: Exception) {
                _userState.value = ApiState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }

    fun login(user: User) {
        viewModelScope.launch {
            _userState.value = ApiState.Loading
            try {
                val response = userApi.login(user)
                _userState.value = ApiState.Success(response)
                UserSesssion.user = response
            }catch (e: Exception) {
                _userState.value = ApiState.Error(e.message ?: "An unknown error occurred")
                Log.d("TAG", e.message ?:"An uknown error occured");
            }
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            _userState.value = ApiState.Loading
            try {
                val response = userApi.updateUser(user)
                _userState.value = ApiState.Success(response)
                UserSesssion.user = response
            } catch (e: Exception) {
                _userState.value = ApiState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }

    fun getDetails(id: Int) {
        viewModelScope.launch {
            _userState.value = ApiState.Loading
            try {
                val response = userApi.getUserById(id)
                _userState.value = ApiState.Success(response)
            }catch (e: Exception) {
                _userState.value = ApiState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }

    fun resetState() {
        _userState.value = ApiState.Idle
    }

}