package com.ekochkov.autodoctestapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ekochkov.autodoctestapp.MainActivity
import com.ekochkov.autodoctestapp.data.entity.Repository
import com.ekochkov.autodoctestapp.databinding.FragmentSearchRepoBinding
import com.ekochkov.autodoctestapp.diff.RepositoryDiff
import com.ekochkov.autodoctestapp.view.adapters.RepositoryAdapter
import com.ekochkov.autodoctestapp.viewModel.SearchRepoViewModel
import com.google.android.material.divider.MaterialDividerItemDecoration

class SearchRepoFragment: Fragment() {

    private lateinit var binding: FragmentSearchRepoBinding
    private lateinit var adapter: RepositoryAdapter

    private val viewModel: SearchRepoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchRepoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.repositoriesLiveData.observe(viewLifecycleOwner) { newList ->
            updateRecyclerView(newList)
            binding.swipeRefresh.isRefreshing = false
        }

        viewModel.toastEventLiveData.observe(viewLifecycleOwner) { text ->
            Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
            binding.swipeRefresh.isRefreshing = false
        }

        adapter = RepositoryAdapter(object : RepositoryAdapter.RepoClickListener {
            override fun onClick(username: String) {
                (activity as MainActivity).launchOwnerFragment(username)
            }
        })
        val divider = MaterialDividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        binding.recyclerView.addItemDecoration(divider)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val layoutManager = (recyclerView.layoutManager as LinearLayoutManager)
                val totalItemsCount = layoutManager.itemCount
                val lastVisiblePosition = layoutManager.findLastCompletelyVisibleItemPosition()
                viewModel.checkOnLastVisibleRepo(lastVisiblePosition, totalItemsCount)

                val firstVisible = layoutManager.findFirstVisibleItemPosition()
                if (firstVisible>0) {
                    binding.fabScrollToTop.visibility = View.VISIBLE
                } else {
                    binding.fabScrollToTop.visibility = View.GONE
                }
            }
        })

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.swipeRefresh()
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) viewModel.newSearchRepositories(query)
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return false
            }
        })

        binding.fabScrollToTop.setOnClickListener {
            binding.recyclerView.smoothScrollToPosition(0)
        }
    }

    private fun updateRecyclerView(newList: List<Repository>) {
        val diff = RepositoryDiff(adapter.list, newList)
        val diffResult = DiffUtil.calculateDiff(diff)
        adapter.list.clear()
        adapter.list.addAll(newList)
        diffResult.dispatchUpdatesTo(adapter)
    }

}