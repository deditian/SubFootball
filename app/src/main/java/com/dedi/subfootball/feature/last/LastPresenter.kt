package com.dedi.subfootball.feature.last

import com.dedi.subfootball.model.ResponseMatch
import com.dedi.subfootball.repository.MatchRepositoryImpl
import com.dedi.subfootball.rx.SchedulerProvider

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subscribers.ResourceSubscriber
import java.util.*

class LastPresenter (private val view: LastView.View,
                     private val matchRepositoryImpl: MatchRepositoryImpl,
                     val scheduler: SchedulerProvider
                                    ) : LastView.Presenter{

    val compositeDisposable = CompositeDisposable()

    override fun getLastMatchData(leagueName: String) {

        compositeDisposable.add(matchRepositoryImpl.getLastMatch(leagueName)
                .observeOn(scheduler.ui())
                .subscribeOn(scheduler.io())
                .subscribeWith(object : ResourceSubscriber<ResponseMatch>(){
                    override fun onComplete() {

                    }

                    override fun onNext(t: ResponseMatch) {
                        view.showLastList(t.events)
                    }

                    override fun onError(e: Throwable) {
                        view.showLastList(Collections.emptyList())
                    }

                })
        )
    }

    override fun onDestroyPresenter() {
        compositeDisposable.dispose()
    }
}