package com.dedi.subfootball.feature.detail

import com.dedi.subfootball.model.ResponseTeams
import com.dedi.subfootball.repository.FavoriteRepositoryImpl
import com.dedi.subfootball.repository.TeamRepositoryImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.ResourceSubscriber

class DetailPresenter (val view: DetailView.View ,
                       val teamRepositoryImpl: TeamRepositoryImpl,
                        val favoriteRepositoryImpl: FavoriteRepositoryImpl) :DetailView.Presenter{
    override fun deleteMatch(id: String) {
        favoriteRepositoryImpl.deleteData(id)
    }

    override fun checkMatch(id: String) {
        view.setFavoriteState(favoriteRepositoryImpl.checkFavorite(id))
    }

    override fun insertMatch(eventId: String, homeId: String, awayId: String) {
        favoriteRepositoryImpl.insertData(eventId, homeId, awayId)
    }

    val compositeDisposable = CompositeDisposable()

    override fun getTeamAway(id: String) {
        compositeDisposable.add(teamRepositoryImpl.getTeamsDetail(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(object: ResourceSubscriber<ResponseTeams>(){
                    override fun onComplete() {

                    }

                    override fun onNext(t: ResponseTeams) {
                        view.showTeamAway(t.teams[0])
                    }

                    override fun onError(t: Throwable) {

                    }
                })
        )
    }

    override fun getTeamHome(id: String) {
        compositeDisposable.add(teamRepositoryImpl.getTeamsDetail(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(object: ResourceSubscriber<ResponseTeams>(){
                    override fun onComplete() {

                    }

                    override fun onNext(t: ResponseTeams) {
                        view.showTeamHome(t.teams[0])
                    }

                    override fun onError(t: Throwable) {

                    }
                })
        )
    }

    override fun onDestroyPresenter() {
        compositeDisposable.dispose()
    }
}