package com.architecture.patterns.mvp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.architecture.patterns.R
import com.architecture.patterns.model.NewsSource

class NewsSourceListItemViewHolder internal constructor(
    itemView: View,
    private val name: TextView,
    private val description: TextView,
    private val listener: Listener
) : RecyclerView.ViewHolder(itemView) {

    interface Listener {
        fun onClick(position: Int)
    }

    companion object {
        fun create(root: ViewGroup, listener: Listener): NewsSourceListItemViewHolder {
            val inflater = LayoutInflater.from(root.context)
            val itemView = inflater.inflate(R.layout.news_source_list_item, root, false)
            val name: TextView = itemView.findViewById(R.id.name)
            val description: TextView = itemView.findViewById(R.id.description)

            return NewsSourceListItemViewHolder(itemView, name, description, listener)
        }
    }

    init {
        itemView.setOnClickListener { listener.onClick(adapterPosition) }
    }

    fun bind(source: NewsSource) {
        name.text = source.name
        description.text = source.description
    }
}
