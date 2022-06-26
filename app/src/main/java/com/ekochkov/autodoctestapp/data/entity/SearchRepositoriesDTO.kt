package com.ekochkov.autodoctestapp.data.entity

data class SearchRepositoriesDTO(
    val incomplete_results: Boolean,
    val items: List<ItemDTO>,
    val total_count: Int
)