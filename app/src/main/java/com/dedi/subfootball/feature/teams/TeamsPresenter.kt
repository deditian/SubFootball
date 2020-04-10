package com.dedi.subfootball.feature.teams

import com.dedi.subfootball.model.ResponseTeams
import com.dedi.subfootball.repository.TeamRepositoryImpl
import com.dedi.subfootball.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subscribers.ResourceSubscriber
import java.util.*

class TeamsPresenter(val mView : TeamsView.View, val teamRepositoryImpl: TeamRepositoryImpl,
                     val scheduler: SchedulerProvider): TeamsView.Presenter {

    val compositeDisposable = CompositeDisposable()

    override fun getTeamData(leagueName: String) {
        mView.showLoading()
        compositeDisposable.add(teamRepositoryImpl.getAllTeam(leagueName)
                .observeOn(scheduler.ui())
                .subscribeOn(scheduler.io())
                .subscribeWith(object: ResourceSubscriber<ResponseTeams>(){
                    override fun onComplete() {
                        mView.hideLoading()
                    }

                    override fun onNext(t: ResponseTeams) {
                        mView.showTeams(t.teams)
                    }

                    override fun onError(t: Throwable?) {
                        mView.showTeams(Collections.emptyList())
                        mView.hideLoading()
                    }

                })
        )
    }

    override fun searchTeam(teamName: String) {
        compositeDisposable.add(teamRepositoryImpl.getTeamBySearch(teamName)
                .observeOn(scheduler.ui())
                .subscribeOn(scheduler.io())
                .subscribeWith(object: ResourceSubscriber<ResponseTeams>(){
                    override fun onComplete() {
                        mView.hideLoading()
                    }

                    override fun onNext(t: ResponseTeams) {
                        mView.showTeams(t.teams ?: Collections.emptyList())
                    }

                    override fun onError(t: Throwable?) {
                        mView.showTeams(Collections.emptyList())
                        mView.hideLoading()
                    }

                })
        )
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }
}