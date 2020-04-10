package com.dedi.subfootball.feature.favoritematch

import com.dedi.subfootball.model.ResponseMatch
import com.dedi.subfootball.model.ResultsItemMatch
import com.dedi.subfootball.repository.FavoriteRepositoryImpl
import com.dedi.subfootball.repository.MatchRepositoryImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.ResourceSubscriber
import java.util.*

class FavoritePresenter (val mView: FavoriteView.View,
                         val matchRepositoryImpl: MatchRepositoryImpl,
                         val favoriteRepositoryImpl: FavoriteRepositoryImpl) : FavoriteView.Presenter{

    val compositeDisposable = CompositeDisposable()

    override fun getFootballMatchData() {
        val favList = favoriteRepositoryImpl.getMatchFromDb()
        var eventList: MutableList<ResultsItemMatch> = mutableListOf()
        for (fav in favList){
            compositeDisposable.add(matchRepositoryImpl.getEventById(fav.idEvent)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribeWith(object: ResourceSubscriber<ResponseMatch>(){
                        override fun onComplete() {

                        }

                        override fun onNext(t: ResponseMatch) {
                            eventList.add(t.events[0])
                            mView.displayFootballMatch(eventList)
                        }

                        override fun onError(t: Throwable?) {
                            mView.displayFootballMatch(Collections.emptyList())

                        }

                    })
            )
        }

        if(favList.isEmpty()){
            mView.displayFootballMatch(eventList)
        }
    }

    override fun onDestroyPresenter() {
        compositeDisposable.dispose()
    }


}