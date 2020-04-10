package com.dedi.subfootball.feature.teamsdetail

import com.dedi.subfootball.db.FavoriteTeam

interface TeamsDetailView {
    interface View{
        fun setFavoriteState(favList: List<FavoriteTeam>)
    }

    interface Presenter{
        fun deleteTeam(id:String)
        fun checkTeam(id:String)
        fun insertTeam(id: String, imgUrl: String)
    }
}