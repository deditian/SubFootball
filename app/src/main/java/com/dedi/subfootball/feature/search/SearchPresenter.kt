package com.dedi.subfootball.feature.search

import com.dedi.subfootball.model.ResponseSearch
import com.dedi.subfootball.repository.MatchRepositoryImpl
import com.dedi.subfootball.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subscribers.ResourceSubscriber
import java.util.*

class SearchPresenter (private val mView: SearchView.View,
                       private val matchRepositoryImpl: MatchRepositoryImpl,
                       private val scheduler: SchedulerProvider): SearchView.Presenter {
    private val compositeDisposable = CompositeDisposable()
    override fun searchMatch(query: String?) {
        mView.showLoading()
        compositeDisposable.add(matchRepositoryImpl.searchMatches(query)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribeWith(object: ResourceSubscriber<ResponseSearch>(){
                    override fun onComplete() {
                        mView.hideLoading()
                    }

                    override fun onNext(t: ResponseSearch) {
                        mView.showSearch(t.event)
                    }

                    override fun onError(t: Throwable) {
                        mView.showSearch(Collections.emptyList())
                        mView.hideLoading()
                    }

                })
        )
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }
}