package com.kumar.hackernews.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.kumar.hackernews.data.HackerNewsItem
import com.kumar.hackernews.ui.fragment.ArticleWebPageFragment
import com.kumar.hackernews.ui.fragment.CommentsFragment

class TabAdapter(fm:FragmentManager,val newsItem: HackerNewsItem) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> ArticleWebPageFragment(newsItem)
            1 -> CommentsFragment(newsItem)
            else -> ArticleWebPageFragment(newsItem)
        }
    }
}