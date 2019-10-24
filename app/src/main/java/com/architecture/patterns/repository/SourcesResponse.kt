package com.architecture.patterns.repository

data class SourcesResponse(
    var status: String? = null,
    var sources: List<ArticleSource> = mutableListOf()
)
