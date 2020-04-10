package com.dedi.subfootball.feature.next

import com.dedi.subfootball.model.ResponseMatch
import com.dedi.subfootball.repository.MatchRepositoryImpl
import com.dedi.subfootball.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subscribers.ResourceSubscriber
import java.util.*

class NextPresenter (val view: NextView.View,
                       val matchRepositoryImpl: MatchRepositoryImpl,
                         val scheduler: SchedulerProvider): NextView.Presenter{

     val compositeDisposable = CompositeDisposable()

    override fun getNextMatchData(leagename: String) {
        compositeDisposable.add(matchRepositoryImpl.getNextMatch(leagename)
                .observeOn(scheduler.ui())
                .subscribeOn(scheduler.io())
                .subscribeWith(object : ResourceSubscriber<ResponseMatch>(){
                    override fun onComplete() {

                    }

                    override fun onNext(t: ResponseMatch) {
                        view.showNextList(t.events)
                    }

                    override fun onError(e: Throwable) {
                        view.showNextList(Collections.emptyList())
                    }

                })
        )
    }

    override fun onDestroyPresenter() {
        compositeDisposable.dispose()
    }
}