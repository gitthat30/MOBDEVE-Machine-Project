package com.mobdeve.machineproject.SQL

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.mobdeve.machineproject.Model.Player

// Handles PlayerDatabase operations
class PlayerDatabase(context: Context) {
    private lateinit var dbHandler : DBHandler

    init {
        this.dbHandler = DBHandler(context)
    }

    fun insertPlayer(player: Player): Long {
        val db = dbHandler.writableDatabase
        //val insert = "INSERT INTO ${DBHandler.TABLE_NAME} (${DBHandler.PLAYER_NAME}, ${DBHandler.PLAYER_IMG}, ${DBHandler.NUM_VIRAL}, ${DBHandler.NUM_SURVIVOR}, ${DBHandler.NUM_SURVIVOR_WINS}, ${DBHandler.NUM_INFECTED}) VALUES ('${player.name}', ${player.playerImg}, ${player.numViral}, ${player.numSurvivor}, ${player.numSurvivorWins}, ${player.numInfected})"
        val content = ContentValues()
        content.put(DBHandler.PLAYER_NAME, player.name)
        content.put(DBHandler.PLAYER_IMG, player.playerImg)
        content.put(DBHandler.NUM_VIRAL, player.numViral)
        content.put(DBHandler.NUM_SURVIVOR, player.numSurvivor)
        content.put(DBHandler.NUM_SURVIVOR_WINS, player.numSurvivorWins)
        content.put(DBHandler.NUM_INFECTED, player.numInfected)

        val key = db.insert(DBHandler.TABLE_NAME, null, content)
        db.close()
        return key
    }

    fun getAllPlayers(): ArrayList<Player> {
        val db = dbHandler.readableDatabase
        val players = ArrayList<Player>()

        val c: Cursor = db.query(
            DBHandler.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            DBHandler.PLAYER_NAME + " ASC",
            null
        )

        while(c.moveToNext()) {
            players.add(Player(
                c.getLong(c.getColumnIndexOrThrow(DBHandler._ID)),
                c.getString(c.getColumnIndexOrThrow(DBHandler.PLAYER_NAME)),
                c.getInt(c.getColumnIndexOrThrow(DBHandler.PLAYER_IMG)),
                c.getInt(c.getColumnIndexOrThrow(DBHandler.NUM_VIRAL)),
                c.getInt(c.getColumnIndexOrThrow(DBHandler.NUM_SURVIVOR)),
                c.getInt(c.getColumnIndexOrThrow(DBHandler.NUM_SURVIVOR_WINS)),
                c.getInt(c.getColumnIndexOrThrow(DBHandler.NUM_INFECTED))))
        }

        c.close()
        db.close()
        return players
    }
}