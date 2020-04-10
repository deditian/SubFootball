package com.dedi.subfootball.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "Favorite.db", null, 1) {

    companion object {
        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
            if (instance == null) {
                instance = MyDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance as MyDatabaseOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Here you create tables
        db.createTable(FavoriteField.TABLE_FAVORITE, true,
                FavoriteField.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                FavoriteField.EVENT_ID to TEXT + UNIQUE,
                FavoriteField.HOME_TEAM_ID to TEXT,
                FavoriteField.AWAY_TEAM_ID to TEXT)
        db.createTable(FavoriteTeam.TEAM_TABLE, true,
                FavoriteTeam.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                FavoriteTeam.TEAM_ID to TEXT,
                FavoriteTeam.TEAM_IMAGE to TEXT)
    }

    override fun onUpgrade(p0: SQLiteDatabase, p1: Int, p2: Int) {
        p0.dropTable(FavoriteField.TABLE_FAVORITE, true)
        p0.dropTable(FavoriteTeam.TEAM_TABLE, true)
    }
}
// Access property for Context
val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)