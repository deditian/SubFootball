package com.dedi.subfootball.repository

import com.dedi.subfootball.db.FavoriteField

interface FavoriteRepository {
    fun getMatchFromDb() : List<FavoriteField>
    fun insertData(eventId: String, homeId: String, awayId: String)
    fun deleteData(eventId: String)
    fun checkFavorite(eventId: String) : List<FavoriteField>
}
