package com.ekochkov.autodoctestapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ekochkov.autodoctestapp.App
import com.ekochkov.autodoctestapp.data.entity.Repository
import com.ekochkov.autodoctestapp.domain.Interactor
import com.ekochkov.autodoctestapp.utils.Constants
import com.ekochkov.autodoctestapp.utils.SingleLiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchRepoViewModel: ViewModel() {

    val repositoriesLiveData = MutableLiveData<List<Repository>>()
    val toastEventLiveData = SingleLiveEvent<String>()
    private val repositoryList = arrayListOf<Repository>()
    private var isLoading = false
    private val addPageValue = 3
    private var searchPage = 1
    private var searchQuery = ""
    private var job: Job? = null

    @Inject
    lateinit var interactor: Interactor

    init {
        App.instance.dagger.inject(this)
    }

    fun newSearchRepositories(query: String) {
        searchQuery = query
        searchPage = 1
        repositoryList.clear()
        searchRepositories(searchQuery)
    }

    fun checkOnLastVisibleRepo(lastVisible: Int, totalItems: Int) {
        if (totalItems>0 && totalItems-lastVisible <= addPageValue) {
            addMoreSearchRepositories()
        }
    }

    fun swipeRefresh() {
        newSearchRepositories(searchQuery)
    }

    private fun addMoreSearchRepositories() {
        searchPage++
        searchRepositories(searchQuery)
    }

    private fun searchRepositories(query: String) {
        if (!isLoading) {
            isLoading = true
            job = CoroutineScope(Dispatchers.IO).launch {
                val result = interactor.searchRepositories(query, searchPage)
                if (result.isNotEmpty()) {
                    repositoryList.addAll(result)
                    repositoriesLiveData.postValue(repositoryList)
                } else {
                    toastEventLiveData.postValue(Constants.LOAD_USER_ERROR)
                }
                isLoading = false
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}