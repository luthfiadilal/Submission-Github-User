package com.example.ggram.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ggram.data.response.ItemsItem
import com.example.ggram.data.response.UserDetailResponse
import com.example.ggram.data.response.UserFollowersResponse
import com.example.ggram.data.response.UserResponse
import com.example.ggram.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    private val _userDetailLiveData = MutableLiveData<UserDetailResponse?>()
    val userDetailLiveData: MutableLiveData<UserDetailResponse?> get() = _userDetailLiveData

    private val _followersLiveData = MutableLiveData<List<ItemsItem>?>()
    val followersLiveData: MutableLiveData<List<ItemsItem>?> get() = _followersLiveData

    private val _followingLiveData = MutableLiveData<List<ItemsItem>?>()
    val followingLiveData: MutableLiveData<List<ItemsItem>?> get() = _followingLiveData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading



    fun fetchUserDetail(username: String) {
        _isLoading.postValue(true)
        val client = ApiConfig.getApiService().getDetailUserAsync(username)
        client.enqueue(object : Callback<UserDetailResponse> {
            override fun onResponse(
                call: Call<UserDetailResponse>,
                response: Response<UserDetailResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _userDetailLiveData.postValue(responseBody)
                    }
                } else {
                    Log.e("ELSE", "OnFilure : ${response.message()}")
                    _isLoading.postValue(false)
                }
            }

            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                Log.e("ELSE", "Onfailure: ${t.message}")
                _isLoading.postValue(false)
            }
        })
    }

    fun findFollowers(username: String) {
        val client = ApiConfig.getApiService().getListFollowers(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                if (response.isSuccessful) {
                    val followers = response.body()
                    if (followers != null) {
                        _followersLiveData.postValue(followers)
                    }
                } else {
                    Log.e("ELSE", "Onfailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                Log.e("ELSE", "Onfailure : ${t.message}")
            }
        })
    }

    fun findFollowing(username: String) {
        val client = ApiConfig.getApiService().getListFollowing(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                if (response.isSuccessful) {
                    val following = response.body()
                    if (following != null) {
                        _followingLiveData.postValue(following)
                    }
                } else {
                    Log.e("ELSE", "Onfailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                Log.e("ELSE", "Onfailure : ${t.message}")
            }

        })
    }


}