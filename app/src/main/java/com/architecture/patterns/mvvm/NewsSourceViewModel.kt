package com.architecture.patterns.mvvm

import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.architecture.patterns.App
import com.architecture.patterns.model.NewsSource
import com.architecture.patterns.mvp.NewsSourceInteractor
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class NewsSourceViewModel(private val interactor: NewsSourceInteractor = NewsSourceInteractor()) : ViewModel() {
    private val showLoading = PublishSubject.create<Boolean>()
    private val showEmpty = PublishSubject.create<Boolean>()
    private val showList = PublishSubject.create<Boolean>()
    private val showError = BehaviorSubject.create<Boolean>()
    private val error = PublishSubject.create<String>()

    fun showLoading(): Flowable<Boolean> = showLoading.toFlowable(BackpressureStrategy.LATEST)
    fun showEmpty(): Flowable<Boolean> = showEmpty.toFlowable(BackpressureStrategy.LATEST)
    fun showList(): Flowable<Boolean> = showList.toFlowable(BackpressureStrategy.LATEST)
    fun showError(): Flowable<Boolean> = showError.toFlowable(BackpressureStrategy.LATEST)
    fun error(): Flowable<String> = error.toFlowable(BackpressureStrategy.LATEST)

    fun newsSources(): Single<List<NewsSource>> =
        interactor.getSources().doOnSubscribe {
            showLoading.onNext(true)
            showEmpty.onNext(false)
            showList.onNext(false)
            showError.onNext(false)
            error.onNext("")
        }.doOnSuccess {
            showList.onNext(true)
            showLoading.onNext(false)
        }.doOnError { e ->
            showLoading.onNext(false)
            showError.onNext(true)
            error.onNext("Failed to load: ${e.message}")
        }

    fun showNewsSourceDetails(id: String) {
        // TODO: Maybe launch new activity or inject a details presenter
        Toast.makeText(App.context, "Show details for: $id", Toast.LENGTH_LONG).show()
    }
}
