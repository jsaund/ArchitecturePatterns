package com.architecture.patterns.mvp

import android.widget.Toast
import com.architecture.patterns.App
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class NewsSourcePresenter(
    private val interactor: NewsSourceInteractor = NewsSourceInteractor(),
    private val worker: Scheduler = Schedulers.io(),
    private val ui: Scheduler = AndroidSchedulers.mainThread(),
    private val disposables: CompositeDisposable = CompositeDisposable()
) {
    private var _viewDelegate: NewsSourceViewDelegate? = null

    fun attach(viewDelegate: NewsSourceViewDelegate) {
        viewDelegate.setListener(object : NewsSourceViewDelegate.Listener {
            override fun onClickNewsSource(id: String) {
                showNewsSourceDetails(id)
            }
        })

        viewDelegate.apply {
            hideEmpty()
            hideError()
            showLoading()
        }

        disposables.add(interactor.getSources()
            .subscribeOn(worker)
            .observeOn(ui)
            .subscribe({ data -> viewDelegate.apply {
                hideLoading()
                if (data.isEmpty()) {
                    showEmpty()
                } else {
                    showList(data)
                }
            } }, { error -> viewDelegate.apply {
                hideLoading()
                hideEmpty()
                hideList()
                showError("Failed to load: ${error.message}")
            }
            }))

        _viewDelegate = viewDelegate
    }

    fun detach() {
        _viewDelegate?.setListener(null)
        disposables.clear()
    }

    fun showNewsSourceDetails(id: String) {
        // TODO: Maybe launch new activity or inject a details presenter
        Toast.makeText(App.context, "Show details for: $id", Toast.LENGTH_LONG).show()
    }
}
