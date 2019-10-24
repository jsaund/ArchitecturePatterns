package com.architecture.patterns.mvp

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.architecture.patterns.model.NewsSource

class NewsSourceAdapter : RecyclerView.Adapter<NewsSourceListItemViewHolder>() {
    private var newsSourceData = emptyList<NewsSource>()
    private var _listener: Listener? = null

    interface Listener {
        fun onClick(source: NewsSource)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsSourceListItemViewHolder =
        NewsSourceListItemViewHolder.create(parent, object : NewsSourceListItemViewHolder.Listener {
            override fun onClick(position: Int) {
                if (position < 0 || position >= itemCount) {
                    return
                }
                _listener?.onClick(newsSourceData[position])
            }
        })

    override fun getItemCount(): Int = newsSourceData.size

    override fun onBindViewHolder(holder: NewsSourceListItemViewHolder, position: Int) {
        if (position < 0 || position >= itemCount) {
            return
        }
        holder.bind(newsSourceData[position])
    }

    fun setData(data: List<NewsSource>) {
        newsSourceData = data
        notifyDataSetChanged()
    }

    fun setLisetener(listener: Listener) {
        _listener = listener
    }
}
