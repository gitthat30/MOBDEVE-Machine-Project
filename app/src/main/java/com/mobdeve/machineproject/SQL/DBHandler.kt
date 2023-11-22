package com.mobdeve.machineproject.SQL

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.mobdeve.machineproject.Model.Player
import com.mobdeve.machineproject.R

class DBHandler(context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object {
        const val DB_NAME = "PlayersDB"
        const val DB_VERSION = 8
        const val TABLE_NAME = "players"
        const val _ID = "id"
        const val PLAYER_NAME = "name"
        const val PLAYER_IMG = "playerimg"
        const val SURVIVOR_WINS = "survivorwins"
        const val SURVIVOR_GAMES_PLAYED = "survivorgamesplayed"
        const val VIRAL_INFECTIONS = "viralinfections"
        const val VIRAL_GAMES_PLAYED = "viralgamesplayed"

        const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
                "$_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$PLAYER_NAME TEXT, " +
                "$PLAYER_IMG INTEGER, " +
                "$SURVIVOR_WINS INTEGER, " +
                "$SURVIVOR_GAMES_PLAYED INTEGER, " +
                "$VIRAL_INFECTIONS INTEGER, " +
                "$VIRAL_GAMES_PLAYED INTEGER)"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        Log.d("test", "onCreate: Created Table")
        db!!.execSQL(CREATE_TABLE)

        //Create 4 insert statements for dummy data
        val insertPlayer1 = "INSERT INTO $TABLE_NAME ($PLAYER_NAME, $PLAYER_IMG, $SURVIVOR_WINS, $SURVIVOR_GAMES_PLAYED, $VIRAL_INFECTIONS, $VIRAL_GAMES_PLAYED) VALUES ('Player 1', ${R.drawable.player1}, 0, 0, 0, 0)"
        val insertPlayer2 = "INSERT INTO $TABLE_NAME ($PLAYER_NAME, $PLAYER_IMG, $SURVIVOR_WINS, $SURVIVOR_GAMES_PLAYED, $VIRAL_INFECTIONS, $VIRAL_GAMES_PLAYED) VALUES ('Player 2', ${R.drawable.player2}, 0, 0, 0, 0)"
        val insertPlayer3 = "INSERT INTO $TABLE_NAME ($PLAYER_NAME, $PLAYER_IMG, $SURVIVOR_WINS, $SURVIVOR_GAMES_PLAYED, $VIRAL_INFECTIONS, $VIRAL_GAMES_PLAYED) VALUES ('Player 3', ${R.drawable.player3}, 0, 0, 0, 0)"
        val insertPlayer4 = "INSERT INTO $TABLE_NAME ($PLAYER_NAME, $PLAYER_IMG, $SURVIVOR_WINS, $SURVIVOR_GAMES_PLAYED, $VIRAL_INFECTIONS, $VIRAL_GAMES_PLAYED) VALUES ('Player 4', ${R.drawable.player4}, 0, 0, 0, 0)"

        db!!.execSQL(insertPlayer1)
        db!!.execSQL(insertPlayer2)
        db!!.execSQL(insertPlayer3)
        db!!.execSQL(insertPlayer4)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //Drop old table then use onCreate
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

}