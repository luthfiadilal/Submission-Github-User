package com.example.ggram.data.retrofit


import com.example.ggram.data.response.ItemsItem
import com.example.ggram.data.response.UserDetailResponse
import com.example.ggram.data.response.UserFollowersResponse
import com.example.ggram.data.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getUsers(
        @Query("q") q : String
    ) : Call<UserResponse>

    // Ini untuk menampilkan data user ketika user klik data dan menampilknya di acticity_detail
    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetailUserAsync(@Path("username") username: String): Call<UserDetailResponse>


    // ini untuk menampilkan data follower user
    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getListFollowers(@Path("username") username: String): Call<List<ItemsItem>>

    // ini untuk menampilkan data following user
    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getListFollowing(@Path("username") username: String): Call<List<ItemsItem>>

}