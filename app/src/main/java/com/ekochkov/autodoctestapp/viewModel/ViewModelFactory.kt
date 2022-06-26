package com.ekochkov.autodoctestapp.viewModel

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ekochkov.autodoctestapp.utils.Constants.EXCEPTION_MESSAGE_ARGUMENT_IS_NULL
import com.ekochkov.autodoctestapp.utils.Constants.EXCEPTION_MESSAGE_UNKNOWN_VIEW_MODEL

class ViewModelFactory(private val username: String?): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val viewModel = when (modelClass) {
            UserViewModel::class.java -> {
                if (username!=null) {
                    UserViewModel(username)
                } else {
                    throw IllegalStateException(EXCEPTION_MESSAGE_ARGUMENT_IS_NULL)
                }
            }
            else -> {
                throw IllegalStateException(EXCEPTION_MESSAGE_UNKNOWN_VIEW_MODEL)
            }
        }
        return viewModel as T
    }
}

fun Fragment.factory(username: String? = null) = ViewModelFactory(username)