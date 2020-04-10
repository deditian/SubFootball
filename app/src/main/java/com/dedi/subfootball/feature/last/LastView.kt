package com.dedi.subfootball.feature.last

import com.dedi.subfootball.model.ResultsItemMatch


interface LastView {
    interface View {
        fun showLastList(data: List<ResultsItemMatch>)
    }


    interface Presenter {
        fun getLastMatchData(leagueName: String = "4328")
        fun onDestroyPresenter()
    }
}