package com.architecture.patterns

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.architecture.patterns.mvphybrid.NewsSourceMVPHybridPresenter
import com.architecture.patterns.mvphybrid.NewsSourceMVPHybridViewDelegate
import com.architecture.patterns.mvvm.NewsSourceMVVMViewDelegate
import com.architecture.patterns.mvvm.NewsSourceViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
//    private lateinit var presenter: NewsSourcePresenter
//    private lateinit var viewModel: NewsSourceViewModel
//    private val disposables = CompositeDisposable()
    private lateinit var presenterHybrid: NewsSourceMVPHybridPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        presenter = NewsSourcePresenter().apply {
//            attach(NewsSourceMVPHybridViewDelegate(findViewById(R.id.root)))
//        }

//        viewModel = ViewModelProviders.of(this)[NewsSourceViewModel::class.java]
//
//        val viewDelegate = NewsSourceMVVMViewDelegate(findViewById(R.id.root))
//        disposables.add(viewDelegate.actions()
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe {
//                viewModel.showNewsSourceDetails(it)
//            })
//        disposables.add(viewModel.error()
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe {
//                viewDelegate.setError(it)
//            })
//        disposables.add(viewModel.showError()
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe { show ->
//                if (show) viewDelegate.showError() else viewDelegate.hideError()
//            })
//        disposables.add(viewModel.showLoading()
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe { show ->
//                if (show) viewDelegate.showLoading() else viewDelegate.hideLoading()
//            })
//        disposables.add(viewModel.showEmpty()
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe { show ->
//                if (show) viewDelegate.showEmpty() else viewDelegate.hideEmpty()
//            })
//        disposables.add(viewModel.showList()
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe { show ->
//                if (show) viewDelegate.showList() else viewDelegate.hideList()
//            })
//        disposables.add(viewModel.newsSources()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe { data ->
//                viewDelegate.setListData(data)
//            })

        presenterHybrid = NewsSourceMVPHybridPresenter()
        presenterHybrid.attach(NewsSourceMVPHybridViewDelegate(findViewById(R.id.root)))
    }

    override fun onDestroy() {
        super.onDestroy()
//        presenter.detach()
//        disposables.clear()
        presenterHybrid.detach()
    }
}
