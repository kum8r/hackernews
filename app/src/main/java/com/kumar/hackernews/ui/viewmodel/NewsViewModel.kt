package com.kumar.hackernews.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.kumar.hackernews.data.HackerNewsItem
import com.kumar.hackernews.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NewsViewModel(private val repository: NewsRepository) : ViewModel() {
    var topStoriesList: LiveData<ArrayList<HackerNewsItem>>
    var latestStoriesList: LiveData<ArrayList<HackerNewsItem>>
    var bestStoriesList: LiveData<ArrayList<HackerNewsItem>>

    init {
        topStoriesList = repository.topStoriesList
        latestStoriesList = repository.topStoriesList
        bestStoriesList = repository.topStoriesList
    }


    fun getTopStories() {
        GlobalScope.launch(Dispatchers.Main) {
            repository.getTopStoriesID()
            repository.getTopStories()
            topStoriesList = repository.topStoriesList
        }
    }

    fun getLatestStories() {
        GlobalScope.launch(Dispatchers.Main) {
            repository.getLatestStories()
            repository.getLatestStories()
            latestStoriesList = repository.latestStoriesList
        }
    }

    fun getBestStories() {
        GlobalScope.launch(Dispatchers.Main) {
            repository.getBestStoriesID()
            repository.getBestStories()
            bestStoriesList = repository.bestStoriesList
        }
    }
}