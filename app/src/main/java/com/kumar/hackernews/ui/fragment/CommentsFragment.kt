package com.kumar.hackernews.ui.fragment

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.kumar.hackernews.R
import com.kumar.hackernews.data.CommentTreeItem
import com.kumar.hackernews.data.HackerNewsItem
import com.kumar.hackernews.repository.NewsRepository
import com.kumar.hackernews.ui.adapter.TreeViewAdapter
import com.kumar.hackernews.ui.viewmodel.CommentsViewModel
import com.kumar.hackernews.ui.viewmodel.CommentsViewModelFactory
import com.unnamed.b.atv.model.TreeNode
import com.unnamed.b.atv.view.AndroidTreeView
import kotlinx.android.synthetic.main.comments_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CommentsFragment(private val newsItem: HackerNewsItem) : Fragment() {
    lateinit var job: Job
    private lateinit var viewModel: CommentsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.comments_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val commentsViewModelFactory = CommentsViewModelFactory(NewsRepository())
        viewModel = ViewModelProvider(this, commentsViewModelFactory).get(CommentsViewModel::class.java)
        setUpTreeView()
    }

    private fun setUpTreeView() {
        val rootNode = TreeNode.root().setExpanded(true)
        val rootNode1 = TreeNode("Click Here To Comments").setExpanded(true)
        rootNode.addChild(rootNode1)
        val tView = AndroidTreeView(activity, rootNode)
        getNestedComments(rootNode1, newsItem)
        container_view.addView(tView.view)
    }

    private fun getNestedComments(node: TreeNode, newsItem: HackerNewsItem) {
        if (newsItem.kids != null) {
            job =   GlobalScope.launch {
                newsItem.kids.forEach {
                    val childNewsItem = viewModel.getStory(it)
                    var parsedText = ""
                    if (childNewsItem.text != null) {
                        parsedText = Html.fromHtml(childNewsItem.text, Html.FROM_HTML_MODE_LEGACY).toString()
                    }
                    val item = CommentTreeItem(parsedText, childNewsItem.by!!, childNewsItem.time!!, !childNewsItem.kids.isNullOrEmpty())
                    val childNode = TreeNode(item).setViewHolder(TreeViewAdapter(context!!))
                    node.addChild(childNode)
                    if (!childNewsItem.kids.isNullOrEmpty()) {
                        getNestedComments(childNode, childNewsItem)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        if (job.isActive) {
            job.cancel()
        }
    }
}



