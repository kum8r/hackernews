package com.kumar.hackernews.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.text.format.DateUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.kumar.hackernews.R
import com.kumar.hackernews.data.HackerNewsItem
import com.kumar.hackernews.ui.adapter.TabAdapter
import kotlinx.android.synthetic.main.activity_detail_news.*
import kotlinx.android.synthetic.main.news_item.*
import java.text.SimpleDateFormat
import java.util.*

class DetailNewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_news)

        newsUrl.visibility = View.INVISIBLE

//        tab_layout.setTabTextColors(Color.parseColor("#000000"), Color.parseColor("#000000"))
//        tab_layout.setSelectedTabIndicator(Color.parseColor("#ff6c47"))

        val newsItem = intent.getSerializableExtra("news") as HackerNewsItem
        setUpTab(newsItem)
        setValuesInViews(newsItem)

    }

    private fun setUpTab(newsItem: HackerNewsItem) {
        val adapter = TabAdapter(supportFragmentManager, newsItem)
        pager.adapter = adapter
        tab_layout.setupWithViewPager(pager)
    }

    private fun setValuesInViews(newsItem: HackerNewsItem) {
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
                time = DateUtils.getRelativeTimeSpanString(
                    date.time,
                    Calendar.getInstance().timeInMillis,
                    DateUtils.MINUTE_IN_MILLIS
                ).toString()
            }
        }


        newsHeading.text = newsItem.title
        newsAuthor.text = "by " + newsItem.by
        newsScore.text = newsItem.score.toString()
        newsUrl.text = newsItem.url
        newsTime.text = time
        newsCommentCount.text = newsItem.kids?.size.toString()
    }
}