package com.architecture.patterns.repository

import io.reactivex.Single

class NewsRepository(private val api: NewsApiRepository.NewsApiService = NewsApiRepository.api) {
    fun getSources(): Single<List<ArticleSource>> = api.getSources().map { it.sources }
}
