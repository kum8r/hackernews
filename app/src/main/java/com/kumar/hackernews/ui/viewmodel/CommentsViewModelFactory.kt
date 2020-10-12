package com.kumar.hackernews.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kumar.hackernews.repository.NewsRepository

class CommentsViewModelFactory(private val newsRepository: NewsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CommentsViewModel(newsRepository) as T
    }
}