package com.dedi.subfootball.feature.playersdetail

import com.dedi.subfootball.model.ResponsePlayersDetail
import com.dedi.subfootball.repository.PlayersRepositoryImpl
import com.dedi.subfootball.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subscribers.ResourceSubscriber

class PlayersDetailPresenter (val mView: PlayersDetailView.View,
                              val playersRepositoryImpl: PlayersRepositoryImpl,
                              val schedulerProvider: SchedulerProvider): PlayersDetailView.Presenter {

    val compositeDisposable = CompositeDisposable()

    override fun getPlayerData(idPlayer: String) {
        compositeDisposable.add(playersRepositoryImpl.getPlayerDetail(idPlayer)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribeWith(object: ResourceSubscriber<ResponsePlayersDetail>(){
                    override fun onComplete() {

                    }

                    override fun onNext(t: ResponsePlayersDetail) {
                        mView.showPlayerDetail(t.players[0])
                    }

                    override fun onError(t: Throwable?) {

                    }

                })
        )
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }
}