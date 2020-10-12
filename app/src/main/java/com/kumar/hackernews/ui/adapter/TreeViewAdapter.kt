package com.kumar.hackernews.ui.adapter

import android.content.Context
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import com.kumar.hackernews.R
import com.kumar.hackernews.data.CommentTreeItem
import com.unnamed.b.atv.model.TreeNode
import kotlinx.android.synthetic.main.comment_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class TreeViewAdapter(val context: Context) : TreeNode.BaseNodeViewHolder<CommentTreeItem>(context) {

    override fun createNodeView(node: TreeNode?, value: CommentTreeItem?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.comment_item, null, false)
        view.tv_comment_item.text = value?.text
        if (value!!.hasChild) {
            view.comment_child.visibility = View.VISIBLE
        }
        view.comment_author.text = value.author

        var time = ""
        val date = Date(value.time.toLong() * 1000)
        val curentDate = Calendar.getInstance()

        val formatter = "dd-MM-yyyy"
        time = SimpleDateFormat(formatter).format(date).toString()
        curentDate.add(Calendar.DAY_OF_YEAR, -7)

        if (date > curentDate.time) {
            val dateFormat = SimpleDateFormat("EEEE")
            time = dateFormat.format(date)
        }

        curentDate.add(Calendar.DATE, -1)

        if (date > curentDate.time) {
            time = DateUtils.getRelativeTimeSpanString(date.time, Calendar.getInstance().timeInMillis, DateUtils.MINUTE_IN_MILLIS).toString()
        }
        view.comment_time.text = time
        return view
    }
}