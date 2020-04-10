package com.dedi.subfootball.feature.favoritematch

import com.dedi.subfootball.model.ResultsItemMatch

interface FavoriteView {
        interface View{
            fun displayFootballMatch(matchList:List<ResultsItemMatch>)

        }
        interface Presenter{
            fun getFootballMatchData()
            fun onDestroyPresenter()
        }

}