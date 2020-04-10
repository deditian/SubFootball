package com.dedi.subfootball.api

import com.dedi.subfootball.model.*
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface MyApi {
    @GET("searchevents.php")
    fun searchMatches(@Query("e") query: String?) : Flowable<ResponseSearch>

    @GET("eventspastleague.php")
    fun getLastFootball (@Query("id") id: String): Flowable <ResponseMatch>

    @GET("eventsnextleague.php")
    fun getNextFootball (@Query("id") id: String): Flowable <ResponseMatch>

    @GET("lookupteam.php")
    fun getTeamDetail (@Query("id") id: String): Flowable <ResponseTeams>

    @GET("lookupevent.php")
    fun getEventById(@Query("id") id:String) : Flowable<ResponseMatch>

    @GET("searchteams.php")
    fun getTeamBySearch(@Query("t") query: String) : Flowable<ResponseTeams>

    @GET("lookup_all_teams.php")
    fun getAllTeam(@Query("id") id:String) : Flowable<ResponseTeams>

    @GET("lookup_all_players.php")
    fun getAllPlayers(@Query("id") id:String?) : Flowable<ResponsePlayers>

    @GET("lookupplayer.php")
    fun getPlayerDetail(@Query("id") id:String?) : Flowable<ResponsePlayersDetail>

}