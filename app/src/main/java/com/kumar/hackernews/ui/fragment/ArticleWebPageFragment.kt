package com.kumar.hackernews.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.kumar.hackernews.R
import com.kumar.hackernews.data.HackerNewsItem
import kotlinx.android.synthetic.main.fragment_article_web_page.*

class ArticleWebPageFragment(private val newsItem: HackerNewsItem) : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_article_web_page, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val webSettings = webview_article.settings
        webSettings.javaScriptEnabled = true
        webview_article.webViewClient = WebViewClient()
        if (newsItem.url != null) {
            Log.d("asasa", newsItem.url)
            webview_article.loadUrl(newsItem.url)
        }
    }

}