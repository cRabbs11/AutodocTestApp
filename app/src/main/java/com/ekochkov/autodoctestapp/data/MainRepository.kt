package com.ekochkov.autodoctestapp.data

import com.ekochkov.autodoctestapp.data.entity.Repository
import com.ekochkov.autodoctestapp.data.entity.SearchRepositoriesDTO
import com.ekochkov.autodoctestapp.data.entity.User
import com.ekochkov.autodoctestapp.data.entity.UserDTO
import com.ekochkov.autodoctestapp.domain.Interactor
import com.ekochkov.autodoctestapp.utils.DTOConverter
import com.ekochkov.autodoctestapp.utils.RetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MainRepository(private val retrofit: RetrofitInterface) {

    suspend fun searchRepositories(query: String, page: Int): List<Repository> {
        return suspendCoroutine {
            retrofit.getRepositoryList(query, page).enqueue(object: Callback<SearchRepositoriesDTO> {
                override fun onResponse(call: Call<SearchRepositoriesDTO>, response: Response<SearchRepositoriesDTO>) {
                    val returnList = arrayListOf<Repository>()
                    val result = response.body()
                    if (result!=null) {
                        returnList.addAll(DTOConverter.fromSearchRepositoriesDTOToRepositoryList(result))
                    }
                    it.resume(returnList)
                }
                override fun onFailure(call: Call<SearchRepositoriesDTO>, t: Throwable) {
                    it.resume(listOf())
                }
            })
        }
    }

    suspend fun getUser(username: String): User? {
        return suspendCoroutine {
            retrofit.getUser(username).enqueue(object:Callback<UserDTO> {
                override fun onResponse(call: Call<UserDTO>, response: Response<UserDTO>) {
                    val result = response.body()
                    if (result!=null) {
                        val user = DTOConverter.fromUserDTOToUser(result)
                        it.resume(user)
                    } else {
                        it.resume(null)
                    }
                }

                override fun onFailure(call: Call<UserDTO>, t: Throwable) {
                    it.resume(null)
                }
            })
        }
    }
}