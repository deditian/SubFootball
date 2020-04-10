package com.dedi.subfootball.repository

import com.dedi.subfootball.api.MyApi
import com.dedi.subfootball.model.ResponsePlayers
import com.dedi.subfootball.model.ResponsePlayersDetail
import io.reactivex.Flowable

class PlayersRepositoryImpl(private val myApi: MyApi){

     fun getAllPlayers(teamId: String?) : Flowable<ResponsePlayers> = myApi.getAllPlayers(teamId)

     fun getPlayerDetail(playerId: String?) : Flowable<ResponsePlayersDetail> = myApi.getPlayerDetail(playerId)
}