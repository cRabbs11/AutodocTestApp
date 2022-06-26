package com.ekochkov.autodoctestapp.data.entity

data class User(
    val nickname: String,
    val bio: String,
    val avatarUrl: String,
    val followers: Int,
    val following: Int,
    val blog: String,
    val twitter: String
)
