package com.ekochkov.autodoctestapp.diff

import androidx.recyclerview.widget.DiffUtil
import com.ekochkov.autodoctestapp.data.entity.Repository

class RepositoryDiff(private val oldList: List<Repository>, private val newList: List<Repository>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return (oldList[oldItemPosition] == newList[newItemPosition])
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return (oldItem.name == newItem.name &&
                oldItem.description == newItem.description &&
                oldItem.language == newItem.language &&
                oldItem.updated_at == oldItem.updated_at)
    }
}