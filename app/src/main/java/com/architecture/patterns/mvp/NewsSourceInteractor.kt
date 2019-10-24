package com.architecture.patterns.mvp

import com.architecture.patterns.model.NewsSource
import com.architecture.patterns.repository.NewsRepository
import io.reactivex.Single

class NewsSourceInteractor(private val repository: NewsRepository = NewsRepository()) {
    fun getSources(): Single<List<NewsSource>> =
        repository.getSources()
            .map { sources ->
                sources.asSequence()
                    .filter { source ->
                        source.id != null && source.name != null
                    }
                    .map {
                        NewsSource(it.id!!, it.name!!, it.description, it.category)
                    }
                    .toList()
            }
}
