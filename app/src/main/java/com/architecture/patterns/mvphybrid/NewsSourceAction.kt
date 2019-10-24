package com.architecture.patterns.mvphybrid

sealed class NewsSourceAction {
    data class OnClickSource(val sourceId: String) : NewsSourceAction()
}