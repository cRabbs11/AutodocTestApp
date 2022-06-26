package com.ekochkov.autodoctestapp.data.entity

data class Repository(
    val name: String,
    val description: String,
    val language: String,
    val languages_url: String,
    val owner: Owner,
    val updated_at: String,
    val stargazers_count: Int,
) {
}