package com.dedi.subfootball.feature.search

import com.dedi.subfootball.model.ResultsItemMatch

interface SearchView {

    interface View{
        fun showLoading()
        fun hideLoading()
        fun showSearch(matchList: List<ResultsItemMatch>)
    }
    interface Presenter{
        fun searchMatch(query: String?)
        fun onDestroy()
    }
}