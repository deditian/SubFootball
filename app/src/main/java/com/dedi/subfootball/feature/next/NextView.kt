package com.dedi.subfootball.feature.next

import com.dedi.subfootball.model.ResultsItemMatch

interface NextView {
    interface View{
        fun showNextList(data : List<ResultsItemMatch>)
    }

    interface Presenter{
        fun getNextMatchData (leagename:String = "4328")
        fun onDestroyPresenter()
    }

}