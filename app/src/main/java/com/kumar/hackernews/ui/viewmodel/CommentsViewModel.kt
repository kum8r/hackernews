package com.kumar.hackernews.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.kumar.hackernews.data.HackerNewsItem
import com.kumar.hackernews.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CommentsViewModel(val repository: NewsRepository) : ViewModel() {

    suspend fun getStory(itemid: Int) : HackerNewsItem {
        return repository.getStoryItem(itemid)
    }
}