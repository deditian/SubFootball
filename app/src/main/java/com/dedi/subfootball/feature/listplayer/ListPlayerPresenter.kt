package com.dedi.subfootball.feature.listplayer

import com.dedi.subfootball.model.ResponsePlayers
import com.dedi.subfootball.repository.PlayersRepositoryImpl
import com.dedi.subfootball.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subscribers.ResourceSubscriber
import java.util.*

class ListPlayerPresenter (val mView: ListPlayerView.View,
                           val playersRepositoryImpl: PlayersRepositoryImpl,
                           val schedulerProvider: SchedulerProvider): ListPlayerView  .Presenter {

    val compositeDisposable = CompositeDisposable()
    override fun getAllPlayer(teamId: String?) {
        compositeDisposable.add(playersRepositoryImpl.getAllPlayers(teamId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribeWith(object: ResourceSubscriber<ResponsePlayers>(){
                    override fun onComplete() {
                    }

                    override fun onNext(t: ResponsePlayers) {
                        mView.showPlayers(t.player)
                    }

                    override fun onError(t: Throwable?) {
                        mView.showPlayers(Collections.emptyList())
                    }

                })
        )
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }

}