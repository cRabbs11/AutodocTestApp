package com.ekochkov.autodoctestapp.utils

import com.ekochkov.autodoctestapp.data.entity.SearchRepositoriesDTO
import com.ekochkov.autodoctestapp.data.entity.UserDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface RetrofitInterface {

    @GET("search/repositories")
    fun getRepositoryList(
        @Query("q") query: String,
        @Query("page") page: Int,
    ): Call<SearchRepositoriesDTO>

    @GET("users/{username}")
    fun getUser(
        @Path("username") username: String,
    ): Call<UserDTO>
}
