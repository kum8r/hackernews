package com.kumar.hackernews.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.kumar.hackernews.R
import com.kumar.hackernews.data.HackerNewsItem
import com.kumar.hackernews.repository.NewsRepository
import com.kumar.hackernews.ui.activity.NewsActivity
import com.kumar.hackernews.ui.adapter.NewsAdapter
import com.kumar.hackernews.ui.viewmodel.NewsViewModel
import com.kumar.hackernews.ui.viewmodel.NewsViewModelFactory
import com.kumar.hackernews.utils.Utils
import kotlinx.android.synthetic.main.top_news_fragment.*

class TopNewsFragment : Fragment() {

    private lateinit var viewModel: NewsViewModel
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: NewsAdapter
    private var retrofitNewsList: ArrayList<HackerNewsItem> = ArrayList()

    var page = 1
    var limit = 10
    var isLoading = false
    var isStart = true
    var hasInternet = true

    companion object {
        fun newInstance() = TopNewsFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.top_news_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val newsActivity = activity as NewsActivity
        viewModel = newsActivity.viewModel
        adapter = NewsAdapter(activity?.applicationContext)
        rv_topnewslist.adapter = adapter

        setRVLayoutManager()
        setRVScrollListener()
        checkInternetConnection()
        createSnackBar()
        getNewsList()
    }


    private fun setRVLayoutManager() {
        linearLayoutManager = LinearLayoutManager(activity)
        rv_topnewslist.layoutManager = linearLayoutManager
        rv_topnewslist.setHasFixedSize(true)
    }

    private fun setRVScrollListener() {
        rv_topnewslist.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = linearLayoutManager.childCount
                val pastVisibleCount = linearLayoutManager.findFirstCompletelyVisibleItemPosition()
                val totalCount = adapter.itemCount

                if (!isLoading) {
                    if (pastVisibleCount + visibleItemCount >= totalCount) {
                        page++
                        if (hasInternet)
                            loadMoreNewsItem()
                    }
                }
            }
        })
    }

    private fun loadMoreNewsItem() {
        pb_loadmore.visibility = View.VISIBLE
        isLoading = true
        val start = (page - 1) * limit
        val end = (page) * limit

        val newsList: ArrayList<HackerNewsItem> = ArrayList()

        if (retrofitNewsList.size >= end) {
            for (i in start..end) {
                newsList.add(retrofitNewsList[i])
            }
        }

        Handler(Looper.getMainLooper()).postDelayed({
            adapter.addAllNewsItems(newsList)
        }, 5000)
        pb_loadmore.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun getNewsList() {
        if (hasInternet) {
            viewModel.getTopStories()

            viewModel.topStoriesList.observe(viewLifecycleOwner, {
                retrofitNewsList = it
                if (isStart && retrofitNewsList.size > limit) {
                    isStart = false
                    loadMoreNewsItem()
                }
            })
        }
    }

    private fun checkInternetConnection(): Boolean {
        hasInternet = Utils(context?.applicationContext).isConnected()
        return hasInternet
    }

    private fun createSnackBar() {
        if (!hasInternet) {
            isLoading = false
            pb_loadmore.visibility = View.INVISIBLE
            val snackbar: Snackbar = Snackbar.make(this.view!!, "No Internet Connection", Snackbar.LENGTH_INDEFINITE)
            snackbar.setAction("Retry", View.OnClickListener {
                if (checkInternetConnection())
                    getNewsList()

            })
            snackbar.show()
        }
    }

}