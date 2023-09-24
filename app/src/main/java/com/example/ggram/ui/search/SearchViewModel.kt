package com.example.ggram.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ggram.data.response.ItemsItem
import com.example.ggram.data.response.UserResponse
import com.example.ggram.data.retrofit.ApiConfig
import com.google.android.material.search.SearchView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel: ViewModel() {
    private  val _usersData = MutableLiveData<List<ItemsItem>>()
    val usersData : LiveData<List<ItemsItem>> get() =  _usersData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    companion object {
        private val TAG = "SearchViewModel"
    }

    fun findUsers(username: String) {
        _isLoading.postValue(true)
        val client = ApiConfig.getApiService().getUsers(username)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        Log.d(TAG, "onResponse: ${responseBody.items}")
                        val users = responseBody.items
                        _usersData.postValue(users)

                    }
                } else {
                    Log.e("ELSE", "onFailure: ${response.message()}")
                }
                _isLoading.postValue(false)
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e(TAG, "onFailure : ${t.message}")
                _isLoading.postValue(false)
            }
        })
    }
}