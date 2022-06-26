package com.ekochkov.autodoctestapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekochkov.autodoctestapp.App
import com.ekochkov.autodoctestapp.data.entity.User
import com.ekochkov.autodoctestapp.domain.Interactor
import com.ekochkov.autodoctestapp.utils.Constants
import com.ekochkov.autodoctestapp.utils.DTOConverter
import com.ekochkov.autodoctestapp.utils.SingleLiveEvent
import kotlinx.coroutines.*
import javax.inject.Inject

class UserViewModel(private val username: String): ViewModel() {

    val userLiveData = MutableLiveData<User>()
    val toastEventLiveData = SingleLiveEvent<String>()
    private var job: Job? = null

    @Inject
    lateinit var interactor: Interactor

    init {
        App.instance.dagger.inject(this)
        getUser(username)
    }

    fun getUser(username: String) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val user = interactor.getUser(username)
            if (user!=null) {
                userLiveData.postValue(user)
            } else {
                toastEventLiveData.postValue(Constants.LOAD_USER_ERROR)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}