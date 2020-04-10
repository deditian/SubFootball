package com.dedi.subfootball.feature.favoriteteam

import com.dedi.subfootball.model.TeamsItem

interface TeamView {
    interface View{
        fun showTeams(teamList: List<TeamsItem>)
    }

    interface Presenter{
        fun getTeamData()
        fun onDestroy()
    }
}