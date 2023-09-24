package com.example.ggram.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ggram.data.response.ItemsItem
import com.example.ggram.data.response.UserResponse
import com.example.ggram.data.retrofit.ApiConfig
import com.example.ggram.pref.SettingPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel: ViewModel() {

    private val _userLiveData = MutableLiveData<List<ItemsItem>>()
    val userLiveData : LiveData<List<ItemsItem>> get() = _userLiveData


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    companion object {
        private const val USER_ID = "luthfi"
    }

    fun findUser(){
        _isLoading.postValue(true)
        val client = ApiConfig.getApiService().getUsers(USER_ID)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val users = responseBody.items
                        _userLiveData.postValue(users)
                    }
                } else {
                    Log.e("ELSE", "Onfailure : ${response.message()}")
                }
                _isLoading.postValue(false)
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e("ELSE", "Onfailure : ${t.message}")
                _isLoading.postValue(false)
            }

        })
    }
}