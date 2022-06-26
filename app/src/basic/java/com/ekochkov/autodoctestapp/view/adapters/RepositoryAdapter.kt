package com.ekochkov.autodoctestapp.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ekochkov.autodoctestapp.R
import com.ekochkov.autodoctestapp.data.entity.Repository
import com.ekochkov.autodoctestapp.view.holders.RepositoryHolder

class RepositoryAdapter(private val clickListener: RepoClickListener): RecyclerView.Adapter<RepositoryHolder>() {

    var list = arrayListOf<Repository>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_repository, parent, false)
        return RepositoryHolder(view)
    }

    override fun onBindViewHolder(holder: RepositoryHolder, position: Int) {
        val repository = list[position]
        holder.binding.name.text = repository.name
        holder.binding.description.text = repository.description
        holder.binding.updateAt.text = repository.updated_at
        holder.binding.stargazersCount.text = repository.stargazers_count.toString()
        holder.binding.languages.text = repository.language

        Glide.with(holder.itemView)
            .load(repository.owner.avatarUrl)
            .centerCrop()
            .into(holder.binding.avatar)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface RepoClickListener {
        fun onClick(username: String)
    }
}