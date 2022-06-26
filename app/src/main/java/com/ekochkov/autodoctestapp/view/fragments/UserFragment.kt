package com.ekochkov.autodoctestapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.ekochkov.autodoctestapp.R
import com.ekochkov.autodoctestapp.data.entity.User
import com.ekochkov.autodoctestapp.databinding.FragmentOwnerBinding
import com.ekochkov.autodoctestapp.utils.Constants.BUNDLE_KEY_USERNAME
import com.ekochkov.autodoctestapp.viewModel.UserViewModel
import com.ekochkov.autodoctestapp.viewModel.factory

class UserFragment: Fragment() {

    private lateinit var binding: FragmentOwnerBinding

    private val viewModel: UserViewModel by viewModels { factory(arguments?.get(BUNDLE_KEY_USERNAME) as String) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOwnerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.userLiveData.observe(viewLifecycleOwner) { user ->
            showUserData(user)
        }

        viewModel.toastEventLiveData.observe(viewLifecycleOwner) { text ->
            Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showUserData(user: User) {
        loadAvatar(user.avatarUrl)

        val nicknameText = "${getText(R.string.nickname)} ${user.nickname}"
        val bioText = "${getText(R.string.bio)} ${user.bio}"
        val followersText = "${getText(R.string.followers)} ${user.followers}"
        val followingText = "${getText(R.string.following)} ${user.following}"
        val blogText = "${getText(R.string.blog)} ${user.blog}"
        val twitterText = "${getText(R.string.twitter)} ${user.twitter}"

        binding.nickname.text = nicknameText
        binding.bio.text = bioText
        binding.followers.text = followersText
        binding.following.text = followingText
        binding.blog.text = blogText
        binding.twitter.text = twitterText
    }

    private fun loadAvatar(url: String) {
        Glide.with(binding.root)
            .load(url)
            .centerCrop()
            .into(binding.avatar)
    }
}