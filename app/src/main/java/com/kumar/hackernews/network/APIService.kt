package com.kumar.hackernews.network

import com.kumar.hackernews.data.HackerNewsItem
import retrofit2.http.GET
import retrofit2.http.Path

interface APIService {

    @GET("topstories.json")
    suspend fun getTopStories(): List<Int>

    @GET("newstories.json")
    suspend fun getLatestStories(): List<Int>

    @GET("beststories.json")
    suspend fun getBeststories(): List<Int>

    @GET("item/{itemId}.json")
    suspend fun getStoryItem(@Path("itemId") itemId: Int) : HackerNewsItem

}