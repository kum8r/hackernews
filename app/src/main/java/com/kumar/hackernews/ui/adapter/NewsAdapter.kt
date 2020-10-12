package com.kumar.hackernews.ui.adapter

import android.content.Context
import android.content.Intent
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kumar.hackernews.R
import com.kumar.hackernews.data.HackerNewsItem
import com.kumar.hackernews.ui.activity.DetailNewsActivity
import kotlinx.android.synthetic.main.news_item.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NewsAdapter(val context: Context?) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    private var newsList: ArrayList<HackerNewsItem> = ArrayList()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(newsItem: HackerNewsItem) {
            var time = ""
            if (newsItem.time != null) {
                val date = Date(newsItem.time.toLong() * 1000)
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
                    time = DateUtils.getRelativeTimeSpanString(date.time,Calendar.getInstance().timeInMillis, DateUtils.MINUTE_IN_MILLIS).toString()
                }
            }


            itemView.newsHeading.text = newsItem.title
            itemView.newsAuthor.text = "by " + newsItem.by
            itemView.newsScore.text = newsItem.score.toString()
            itemView.newsUrl.text = newsItem.url
            itemView.newsTime.text = time
            itemView.newsCommentCount.text = newsItem.kids?.size.toString()

            itemView.setOnClickListener {
                val intent = Intent(context, DetailNewsActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                intent.putExtra("news", newsItem)
                context?.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(newsList[position])
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    fun addAllNewsItems(newsItemList: ArrayList<HackerNewsItem>) {
        newsList.addAll(newsItemList)
        notifyDataSetChanged()
    }
}