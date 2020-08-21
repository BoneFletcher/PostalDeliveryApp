package com.sdv.presentation.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.sdv.presentation.ui.pager.posts.PostsFragment
import com.sdv.presentation.ui.pager.user.UsersFragment


class PostPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private var userTitle = ""
    private var postTitle = ""
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                UsersFragment()
            } else -> {
                return PostsFragment()
            }
        }
    }

    fun setTitleName(userTitle: String, postTitle: String) {
        this.userTitle = userTitle
        this.postTitle = postTitle
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): String? {
        return when (position) {
            0 -> userTitle
            else -> {
                return postTitle
            }
        }
    }

}