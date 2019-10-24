package com.architecture.patterns.mvvm

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.architecture.patterns.R
import com.architecture.patterns.model.NewsSource
import com.architecture.patterns.mvp.NewsSourceAdapter
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject

class NewsSourceMVVMViewDelegate(root: View) {
    private val loading: ProgressBar = root.findViewById(R.id.loading)
    private val newsSourceList: RecyclerView = root.findViewById(R.id.list)
    private val empty: TextView = root.findViewById(R.id.empty)
    private val error: TextView = root.findViewById(R.id.error)
    private val adapter = NewsSourceAdapter()
    private val clickNewsSourceAction = PublishSubject.create<String>()

    init {
        newsSourceList.layoutManager = LinearLayoutManager(root.context, RecyclerView.VERTICAL, false)
        newsSourceList.adapter = adapter
        adapter.setLisetener(object : NewsSourceAdapter.Listener {
            override fun onClick(source: NewsSource) {
                clickNewsSourceAction.onNext(source.id)
            }
        })
    }

    fun actions(): Flowable<String> = clickNewsSourceAction.toFlowable(BackpressureStrategy.LATEST)

    fun showLoading() {
        loading.visibility = View.VISIBLE
    }

    fun hideLoading() {
        loading.visibility = View.GONE
    }

    fun setListData(data: List<NewsSource>) {
        adapter.setData(data)
    }

    fun showList() {
        newsSourceList.visibility = View.VISIBLE
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

    fun setError(message: String) {
        error.text = message
    }

    fun showError() {
        error.visibility = View.VISIBLE
    }

    fun hideError() {
        error.visibility = View.GONE
    }
}
