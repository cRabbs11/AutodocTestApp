package com.ekochkov.autodoctestapp.data.entity

import java.io.Serializable

data class Owner(
    val id: Int,
    val avatarUrl: String,
    val login: String,
): Serializable {
}