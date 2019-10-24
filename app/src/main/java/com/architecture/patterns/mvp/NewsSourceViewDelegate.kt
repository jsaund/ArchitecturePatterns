package com.architecture.patterns.mvp

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.architecture.patterns.R
import com.architecture.patterns.model.NewsSource

class NewsSourceViewDelegate(root: View) {
    private val loading: ProgressBar = root.findViewById(R.id.loading)
    private val newsSourceList: RecyclerView = root.findViewById(R.id.list)
    private val empty: TextView = root.findViewById(R.id.empty)
    private val error: TextView = root.findViewById(R.id.error)
    private val adapter = NewsSourceAdapter()

    private var listener: Listener? = null

    init {
        newsSourceList.layoutManager = LinearLayoutManager(root.context, RecyclerView.VERTICAL, false)
        newsSourceList.adapter = adapter
        adapter.setLisetener(object : NewsSourceAdapter.Listener {
            override fun onClick(source: NewsSource) {
                listener?.onClickNewsSource(source.id)
            }
        })
    }

    interface Listener {
        fun onClickNewsSource(id: String)
    }

    fun showLoading() {
        loading.visibility = View.VISIBLE
    }

    fun hideLoading() {
        loading.visibility = View.GONE
    }

    fun showList(data: List<NewsSource>) {
        newsSourceList.visibility = View.VISIBLE
        adapter.setData(data)
    }

    fun hideList() {
        newsSourceList.visibility = View.GONE
    }

    fun showEmpty() {
        empty.visibility = View.VISIBLE
    }

    fun hideEmpty() {
        empty.visibility = View.GONE
    }

    fun showError(message: String) {
        error.visibility = View.VISIBLE
        error.text = message
    }

    fun hideError() {
        error.visibility = View.GONE
    }

    fun setListener(newsSourceListener: Listener?) {
        listener = newsSourceListener
    }
}
