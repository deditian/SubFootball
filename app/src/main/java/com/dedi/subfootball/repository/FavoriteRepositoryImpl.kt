package com.dedi.subfootball.repository

import android.content.Context

import com.dedi.subfootball.db.FavoriteField
import com.dedi.subfootball.db.FavoriteTeam
import com.dedi.subfootball.db.database
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class FavoriteRepositoryImpl (private val context: Context) : FavoriteRepository  {

    override fun getMatchFromDb(): List<FavoriteField> {
        lateinit var favoriteList :List<FavoriteField>
        context.database.use {
            val result = select(FavoriteField.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<FavoriteField>())
            favoriteList = favorite
        }
        return favoriteList
    }
    fun getTeamFromDb(): List<FavoriteTeam> {
        lateinit var favoriteList :List<FavoriteTeam>
        context.database.use {
            val result = select(FavoriteTeam.TEAM_TABLE)
            val favorite = result.parseList(classParser<FavoriteTeam>())
            favoriteList = favorite
        }
        return favoriteList
    }

    override fun insertData(eventId: String, homeId: String, awayId: String) {
        context.database.use {
            insert(FavoriteField.TABLE_FAVORITE,
                    FavoriteField.EVENT_ID to eventId,
                    FavoriteField.HOME_TEAM_ID to homeId,
                    FavoriteField.AWAY_TEAM_ID to awayId)

        }
    }

    override fun deleteData(eventId: String) {
        context.database.use{
            delete(FavoriteField.TABLE_FAVORITE, "(EVENT_ID = {id})",
                    "id" to eventId)
        }
    }

    override fun checkFavorite(eventId: String): List<FavoriteField> {
        return context.database.use {
            val result = select(FavoriteField.TABLE_FAVORITE)
                    .whereArgs("(EVENT_ID = {id})",
                            "id" to eventId)
            val favorite = result.parseList(classParser<FavoriteField>())
            favorite
        }
    }


    fun deleteTeamsData(teamId: String) {
        context.database.use{
            delete(FavoriteTeam.TEAM_TABLE, "(TEAM_ID = {id})",
                    "id" to teamId)
        }
    }

    fun checkFavTeam(teamId: String): List<FavoriteTeam> {
        return context.database.use {
            val result = select(FavoriteTeam.TEAM_TABLE)
                    .whereArgs("(TEAM_ID = {id})",
                            "id" to teamId)
            val favorite = result.parseList(classParser<FavoriteTeam>())
            favorite
        }
    }

    fun insertTeamData(teamId: String, imgUrl: String) {
        context.database.use {
            insert(FavoriteTeam.TEAM_TABLE,
                    FavoriteTeam.TEAM_ID to teamId,
                    FavoriteTeam.TEAM_IMAGE to imgUrl)

        }

    }
}