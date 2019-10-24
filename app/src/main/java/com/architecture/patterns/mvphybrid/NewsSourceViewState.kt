package com.architecture.patterns.mvphybrid

import com.architecture.patterns.model.NewsSource

sealed class NewsSourceViewState {
    object Loading : NewsSourceViewState()
    object Empty : NewsSourceViewState()
    data class Error(val message: String) : NewsSourceViewState()
    data class Data(val data: List<NewsSource>) : NewsSourceViewState()
}