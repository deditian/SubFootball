package com.dedi.subfootball.feature.teams

import com.dedi.subfootball.model.TeamsItem

interface TeamsView {
    interface View{
        fun showTeams(teamList: List<TeamsItem>)
        fun hideLoading()
        fun showLoading()

    }
    interface Presenter{
        fun getTeamData(leagueName: String)
        fun searchTeam(teamName: String)
        fun onDestroy()
    }
}