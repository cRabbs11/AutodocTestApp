package com.ekochkov.autodoctestapp.domain

import com.ekochkov.autodoctestapp.data.MainRepository

class Interactor(private val repository: MainRepository) {

    suspend fun getUser(username: String) = repository.getUser(username)

    suspend fun searchRepositories(query: String, page: Int) = repository.searchRepositories(query, page)

}