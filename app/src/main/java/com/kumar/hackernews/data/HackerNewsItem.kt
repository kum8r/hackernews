package com.kumar.hackernews.data

import java.io.Serializable

data class HackerNewsItem(
    val by: String?,
    val descendants: Int?,
    val id: Int?,
    val kids: List<Int>?,
    val score: Int?,
    val time: Int?,
    val title: String?,
    val type: String?,
    val url: String?,
    val text: String?,
) : Serializable