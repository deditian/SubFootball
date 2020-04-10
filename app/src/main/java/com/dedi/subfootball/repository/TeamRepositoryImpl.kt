package com.dedi.subfootball.repository

import com.dedi.subfootball.api.MyApi
import com.dedi.subfootball.model.ResponseTeams
import io.reactivex.Flowable

class TeamRepositoryImpl(val myApi: MyApi) {
   fun getTeamsDetail(id: String = "0"): Flowable<ResponseTeams> = myApi.getTeamDetail(id)
   fun getAllTeam(id: String) : Flowable<ResponseTeams> = myApi.getAllTeam(id)
   fun getTeamBySearch(query: String): Flowable<ResponseTeams> = myApi.getTeamBySearch(query)
   fun getTeams(id: String="0"): Flowable<ResponseTeams> = myApi.getAllTeam(id)
}