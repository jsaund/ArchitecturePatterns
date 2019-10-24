package com.architecture.patterns.mvphybrid

import android.widget.Toast
import com.architecture.patterns.App
import com.architecture.patterns.mvp.NewsSourceInteractor
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class NewsSourceMVPHybridPresenter(
    private val interactor: NewsSourceInteractor = NewsSourceInteractor(),
    private val worker: Scheduler = Schedulers.io(),
    private val ui: Scheduler = AndroidSchedulers.mainThread(),
    private val disposables: CompositeDisposable = CompositeDisposable()
) {
    private val state = BehaviorSubject.create<NewsSourceViewState>()

    fun attach(viewDelegate: NewsSourceMVPHybridViewDelegate) {
        disposables.add(viewDelegate.actions()
            .observeOn(ui)
            .subscribe { action ->
                when (action) {
                    is NewsSourceAction.OnClickSource -> showNewsSourceDetails(action.sourceId)
                }
            })
        disposables.add(state.observeOn(ui)
            .subscribe {
                viewDelegate.bind(it)
            })

        state.onNext(NewsSourceViewState.Loading)

        disposables.add(
            interactor.getSources()
                .subscribeOn(worker)
                .observeOn(ui)
                .subscribe({ data ->
                    viewDelegate.apply {
                        if (data.isEmpty()) {
                            state.onNext(NewsSourceViewState.Empty)
                        } else {
                            state.onNext(NewsSourceViewState.Data(data))
                        }
                    }
                }, { error ->
                    state.onNext(NewsSourceViewState.Error("Failed to load: ${error.message}"))
                })
        )
    }

    fun detach() {
        disposables.clear()
    }

    fun showNewsSourceDetails(id: String) {
        // TODO: Maybe launch new activity or inject a details presenter
        Toast.makeText(App.context, "Show details for: $id", Toast.LENGTH_LONG).show()
    }
}
