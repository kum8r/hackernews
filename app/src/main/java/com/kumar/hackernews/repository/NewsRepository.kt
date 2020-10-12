package com.kumar.hackernews.repository

import androidx.lifecycle.MutableLiveData
import com.kumar.hackernews.data.HackerNewsItem
import com.kumar.hackernews.network.RetrofitInstance

class NewsRepository {
    var topStoriesIdList = ArrayList<Int>()
    val topStoriesList = MutableLiveData<ArrayList<HackerNewsItem>>()
    var topStoriesItemArrayList = ArrayList<HackerNewsItem>()

    var latestStoriesIdList = ArrayList<Int>()
    val latestStoriesList = MutableLiveData<ArrayList<HackerNewsItem>>()
    var latestStoriesItemArrayList = ArrayList<HackerNewsItem>()

    var bestStoriesIdList = ArrayList<Int>()
    val bestStoriesList = MutableLiveData<ArrayList<HackerNewsItem>>()
    var bestStoriesItemArrayList = ArrayList<HackerNewsItem>()

    suspend fun getTopStoriesID() {
        topStoriesIdList = RetrofitInstance.api.getTopStories() as ArrayList<Int>
    }

    suspend fun getLatestStoriesID() {
        latestStoriesIdList = RetrofitInstance.api.getLatestStories() as ArrayList<Int>
    }

    suspend fun getBestStoriesID() {
        bestStoriesIdList = RetrofitInstance.api.getBeststories() as ArrayList<Int>
    }

    suspend fun getStoryItem(itemId: Int): HackerNewsItem {
        return RetrofitInstance.api.getStoryItem(itemId)
    }

    suspend fun getTopStories() {
        topStoriesIdList.forEach {
            val item = getStoryItem(it)
            topStoriesItemArrayList.add(item)
            topStoriesList.value = topStoriesItemArrayList
        }
    }

    suspend fun getLatestStories() {
        latestStoriesIdList.forEach {
            val item = getStoryItem(it)
            latestStoriesItemArrayList.add(item)
            latestStoriesList.value = latestStoriesItemArrayList
        }
    }

    suspend fun getBestStories() {
        bestStoriesIdList.forEach {
            val item = getStoryItem(it)
            bestStoriesItemArrayList.add(item)
            bestStoriesList.value = bestStoriesItemArrayList
        }
    }

}