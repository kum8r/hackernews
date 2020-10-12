package com.kumar.hackernews.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kumar.hackernews.R
import com.kumar.hackernews.repository.NewsRepository
import com.kumar.hackernews.ui.adapter.NewsAdapter
import com.kumar.hackernews.ui.fragment.BestNewsFragment
import com.kumar.hackernews.ui.fragment.LatestNewsFragment
import com.kumar.hackernews.ui.fragment.TopNewsFragment
import com.kumar.hackernews.ui.viewmodel.NewsViewModel
import com.kumar.hackernews.ui.viewmodel.NewsViewModelFactory

class NewsActivity : AppCompatActivity() {

    val topNewsFragment = TopNewsFragment()
    val bestNewsFragment = BestNewsFragment()
    val latestNewsFragment = LatestNewsFragment()
    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        viewModel =
            ViewModelProvider(this, NewsViewModelFactory(NewsRepository())).get(NewsViewModel::class.java)
        changeFragment(topNewsFragment)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.news_combo_box, menu)
        val item = menu?.findItem(R.id.news_combobox)
        val spinner:Spinner = item?.actionView as Spinner
        val comboList = ArrayList<String>()
        comboList.add("Top News")
        comboList.add("Best News")
        comboList.add("Latest News")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, comboList)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                when (adapter.getItem(p2) as String) {
                    "Latest News" -> changeFragment(latestNewsFragment)
                    "Top News" -> changeFragment(topNewsFragment)
                    "Best News" -> changeFragment(bestNewsFragment)
                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
        return super.onCreateOptionsMenu(menu)
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.news_fragment_container, fragment, fragment.javaClass.simpleName)
            .commit()
    }
}