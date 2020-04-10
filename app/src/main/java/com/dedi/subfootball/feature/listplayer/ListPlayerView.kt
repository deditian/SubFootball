package com.dedi.subfootball.feature.listplayer

import com.dedi.subfootball.model.PlayersItem

interface ListPlayerView {
    interface View{
        fun showPlayers(playerList: List<PlayersItem>)

    }
    interface Presenter{
        fun getAllPlayer(teamId: String?)
        fun onDestroy()
    }
}