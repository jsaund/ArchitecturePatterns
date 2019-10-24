package com.architecture.patterns.mvc

import android.app.Activity
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.architecture.patterns.R
import com.architecture.patterns.model.NewsSource
import com.architecture.patterns.mvp.NewsSourceAdapter
import com.architecture.patterns.mvp.NewsSourceViewDelegate
import com.architecture.patterns.repository.NewsApiRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class NewsSourceController(activity: Activity) {
    private val loading: ProgressBar = activity.findViewById(R.id.loading)
    private val newsSourceList: RecyclerView = activity.findViewById(R.id.list)
    private val empty: TextView = activity.findViewById(R.id.empty)
    private val error: TextView = activity.findViewById(R.id.error)
    private val adapter = NewsSourceAdapter()
    private val disposables = CompositeDisposable()

    private var listener: NewsSourceViewDelegate.Listener? = null

    init {
        newsSourceList.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        newsSourceList.adapter = adapter
        adapter.setLisetener(object : NewsSourceAdapter.Listener {
            override fun onClick(source: NewsSource) {
                listener?.onClickNewsSource(source.id)
            }
        })
    }

    fun showNewsSources() {
        loading.visibility = View.VISIBLE
        newsSourceList.visibility = View.GONE
        empty.visibility = View.GONE
        error.visibility = View.GONE

        disposables.add(NewsApiRepository.api.getSources()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe( { data ->
                loading.visibility = View.GONE
                val newsSources: MutableList<NewsSource> = mutableListOf()
                for (s in data.sources) {
                    if (s.id != null && s.name != null) {
                        newsSources.add(NewsSource(s.id, s.name, s.description, s.category))
                    }
                }

                if (newsSources.isEmpty()) {
                    empty.visibility = View.VISIBLE
                } else {
                    newsSourceList.visibility = View.VISIBLE
                    adapter.setData(newsSources)
                }
            }, { e ->
                loading.visibility = View.GONE
                error.visibility = View.VISIBLE
                error.text = "Failed to load data: ${e.message}"
            }))
    }

    fun destroy() {
        listener = null
        disposables.clear()
    }
}