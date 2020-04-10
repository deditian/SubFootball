package com.dedi.subfootball.feature.playersdetail

import com.dedi.subfootball.model.PlayersDetailItem

interface PlayersDetailView {
    interface View{
        fun showPlayerDetail(player: PlayersDetailItem)
    }
    interface Presenter{
        fun getPlayerData(idPlayer: String)
        fun onDestroy()
    }
}