package com.architecture.patterns.mvphybrid

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

class NewsSourceMVPHybridViewDelegate(root: View) {
    private val loading: ProgressBar = root.findViewById(R.id.loading)
    private val newsSourceList: RecyclerView = root.findViewById(R.id.list)
    private val empty: TextView = root.findViewById(R.id.empty)
    private val error: TextView = root.findViewById(R.id.error)
    private val adapter = NewsSourceAdapter()
    private val actions = PublishSubject.create<NewsSourceAction>()

    init {
        newsSourceList.layoutManager = LinearLayoutManager(root.context, RecyclerView.VERTICAL, false)
        newsSourceList.adapter = adapter
        adapter.setLisetener(object : NewsSourceAdapter.Listener {
            override fun onClick(source: NewsSource) {
                actions.onNext(NewsSourceAction.OnClickSource(source.id))
            }
        })
    }

    fun actions(): Flowable<NewsSourceAction> = actions.toFlowable(BackpressureStrategy.LATEST)

    fun bind(state: NewsSourceViewState) {
        when (state) {
            NewsSourceViewState.Loading -> {
                loading.visibility = View.VISIBLE
                empty.visibility = View.GONE
                error.visibility = View.GONE
                newsSourceList.visibility = View.GONE
            }
            NewsSourceViewState.Empty -> {
                loading.visibility = View.GONE
                empty.visibility = View.VISIBLE
                error.visibility = View.GONE
                newsSourceList.visibility = View.GONE
            }
            is NewsSourceViewState.Error -> {
                loading.visibility = View.GONE
                empty.visibility = View.GONE
                error.visibility = View.VISIBLE
                newsSourceList.visibility = View.GONE

                error.text = state.message
            }
            is NewsSourceViewState.Data -> {
                loading.visibility = View.GONE
                empty.visibility = View.GONE
                error.visibility = View.GONE
                newsSourceList.visibility = View.VISIBLE
                adapter.setData(state.data)
            }
        }
    }
}