package com.dedi.subfootball.feature.detail

import com.dedi.subfootball.db.FavoriteField
import com.dedi.subfootball.model.TeamsItem

interface DetailView {
    interface View {
        fun showTeamHome(team: TeamsItem)
        fun showTeamAway(team: TeamsItem)
        fun setFavoriteState(favList:List<FavoriteField>)
    }
    interface Presenter {
        fun getTeamAway(id: String)
        fun getTeamHome(id: String)
        fun onDestroyPresenter()
        fun deleteMatch(id:String)
        fun checkMatch(id:String)
        fun insertMatch(eventId: String, homeId: String, awayId: String)
    }
}