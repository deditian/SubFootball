package com.dedi.subfootball.model

import com.google.gson.annotations.SerializedName


data class ResponsePlayers (
    @SerializedName("player")
    var player: List<PlayersItem>
    )