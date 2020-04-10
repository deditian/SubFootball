package com.dedi.subfootball.model

import com.google.gson.annotations.SerializedName



data class ResponsePlayersDetail (
    @SerializedName("players") var players: List <PlayersDetailItem>
)