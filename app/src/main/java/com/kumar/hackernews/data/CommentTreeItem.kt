package com.kumar.hackernews.data

data class CommentTreeItem(
    val text: String,
    val author: String,
    val time: Int,
    val hasChild: Boolean
)
