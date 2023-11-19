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
        const val DB_VERSION = 6
        const val TABLE_NAME = "players"
        const val _ID = "id"
        const val PLAYER_NAME = "name"
        const val PLAYER_IMG = "playerimg"
        const val NUM_VIRAL = "numviral"
        const val NUM_SURVIVOR = "numsurvivor"
        const val NUM_SURVIVOR_WINS = "numsurvivorwins"
        const val NUM_INFECTED = "numinfected"

        const val CREATE_TABLE = ("CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
                "$_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$PLAYER_NAME TEXT," +
                "$PLAYER_IMG INTEGER," +
                "$NUM_VIRAL INTEGER," +
                "$NUM_SURVIVOR INTEGER," +
                "$NUM_SURVIVOR_WINS INTEGER," +
                "$NUM_INFECTED INTEGER)")

    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        Log.d("test", "onCreate: Created Table")
        db!!.execSQL(CREATE_TABLE)

        val insertPlayer1 = "INSERT INTO $TABLE_NAME ($PLAYER_NAME, $PLAYER_IMG, $NUM_VIRAL, $NUM_SURVIVOR, $NUM_SURVIVOR_WINS, $NUM_INFECTED) VALUES ('P1', " + R.drawable.player1 + ", 0, 0, 0, 0)"
        val insertPlayer2 = "INSERT INTO $TABLE_NAME ($PLAYER_NAME, $PLAYER_IMG, $NUM_VIRAL, $NUM_SURVIVOR, $NUM_SURVIVOR_WINS, $NUM_INFECTED) VALUES ('Test', " + R.drawable.player2 + ", 0, 0, 0, 0)"
        val insertPlayer3 = "INSERT INTO $TABLE_NAME ($PLAYER_NAME, $PLAYER_IMG, $NUM_VIRAL, $NUM_SURVIVOR, $NUM_SURVIVOR_WINS, $NUM_INFECTED) VALUES ('3fasf', " + R.drawable.player3 + ", 0, 0, 0, 0)"
        val insertPlayer4 = "INSERT INTO $TABLE_NAME ($PLAYER_NAME, $PLAYER_IMG, $NUM_VIRAL, $NUM_SURVIVOR, $NUM_SURVIVOR_WINS, $NUM_INFECTED) VALUES ('afas 2', " + R.drawable.player4 + ", 0, 0, 0, 0)"

        db.execSQL(insertPlayer1)
        db.execSQL(insertPlayer2)
        db.execSQL(insertPlayer3)
        db.execSQL(insertPlayer4)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //Drop old table then use onCreate
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

}