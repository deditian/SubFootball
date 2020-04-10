package com.dedi.subfootball.feature.favoriteteam

import com.dedi.subfootball.model.ResponseTeams
import com.dedi.subfootball.model.TeamsItem
import com.dedi.subfootball.repository.FavoriteRepositoryImpl
import com.dedi.subfootball.repository.TeamRepositoryImpl
import com.dedi.subfootball.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subscribers.ResourceSubscriber
import java.util.*

class TeamPresenter (val mView: TeamView.View,
                     val favoriteRepositoryImpl: FavoriteRepositoryImpl,
                     val teamRepositoryImpl: TeamRepositoryImpl,
                      val schedulerProvider: SchedulerProvider) : TeamView.Presenter{
    val compositeDisposable = CompositeDisposable()
    override fun getTeamData() {
        val teamList = favoriteRepositoryImpl.getTeamFromDb()
        var teamLists: MutableList<TeamsItem> = mutableListOf()
        for (fav in teamList){
            compositeDisposable.add(teamRepositoryImpl.getTeamsDetail(fav.idTeam)
                    .observeOn(schedulerProvider.ui())
                    .subscribeOn(schedulerProvider.io())
                    .subscribeWith(object: ResourceSubscriber<ResponseTeams>(){
                        override fun onComplete() {
                        }

                        override fun onNext(t: ResponseTeams) {
                            teamLists.add(t.teams[0])
                            mView.showTeams(teamLists)
                        }

                        override fun onError(t: Throwable?) {
                            mView.showTeams(Collections.emptyList())
                        }

                    })
            )
        }

        if(teamList.isEmpty()){
            mView.showTeams(teamLists)
        }
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }

}