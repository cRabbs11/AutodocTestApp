package com.ekochkov.autodoctestapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.ekochkov.autodoctestapp.databinding.ActivityMainBinding
import com.ekochkov.autodoctestapp.utils.Constants.BUNDLE_KEY_USERNAME
import com.ekochkov.autodoctestapp.view.fragments.AboutFragment
import com.ekochkov.autodoctestapp.view.fragments.UserFragment
import com.ekochkov.autodoctestapp.view.fragments.SearchRepoFragment

private const val TAG_SEARCH_REPO_FRAGMENT = "search_repo_fragment"
private const val TAG_ABOUT_FRAGMENT = "about_fragment"
private const val TAG_OWNER_FRAGMENT = "owner_fragment"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.search_repo -> {
                    launchSearchRepoFragment()
                    true
                }
                R.id.about -> {
                    launchAboutFragment()
                    true
                }
                else -> false
            }
        }

        launchSearchRepoFragment()
    }

    fun launchSearchRepoFragment() {
        val fragment = getExistFragmentByTag(TAG_SEARCH_REPO_FRAGMENT)?: SearchRepoFragment()
        launchFragment(fragment, TAG_SEARCH_REPO_FRAGMENT)
    }

    fun launchOwnerFragment(username: String) {
        val bundle = Bundle()
        bundle.putString(BUNDLE_KEY_USERNAME, username)

        val fragment = getExistFragmentByTag(TAG_OWNER_FRAGMENT)?: UserFragment()
        fragment.arguments = bundle
        launchFragment(fragment, TAG_OWNER_FRAGMENT)
    }


    fun launchAboutFragment() {
        val fragment = getExistFragmentByTag(TAG_ABOUT_FRAGMENT)?: AboutFragment()
        launchFragment(fragment, TAG_ABOUT_FRAGMENT)
    }

    override fun onBackPressed() {
        val lastFragmentIndex = supportFragmentManager.backStackEntryCount-1
        if (lastFragmentIndex<=0) {
            super.onBackPressed()
            finish()
        } else {
            val lastFragmentTag = supportFragmentManager.getBackStackEntryAt(lastFragmentIndex).name
            if (lastFragmentTag==TAG_OWNER_FRAGMENT) {
                super.onBackPressed()
            } else {
                super.onBackPressed()
                finish()
            }
        }
    }

    private fun getExistFragmentByTag(tag: String): Fragment? {
        return supportFragmentManager.findFragmentByTag(tag)
    }

    private fun launchFragment(fragment: Fragment, tag: String?) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment, tag)
            .addToBackStack(tag)
            .commit()
    }
}