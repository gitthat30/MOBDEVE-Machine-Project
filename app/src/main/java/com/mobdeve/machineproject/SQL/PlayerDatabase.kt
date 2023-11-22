package com.mobdeve.machineproject.SQL

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
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
        content.put(DBHandler.SURVIVOR_WINS, player.numSurvivorWins)
        content.put(DBHandler.SURVIVOR_GAMES_PLAYED, player.numSurvivorPlayed)
        content.put(DBHandler.VIRAL_INFECTIONS, player.numViralInfections)
        content.put(DBHandler.VIRAL_GAMES_PLAYED, player.numViralPlayed)

        val key = db.insert(DBHandler.TABLE_NAME, null, content)
        Log.v("TEST", "Inserted Player with ID: $key")
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
                c.getInt(c.getColumnIndexOrThrow(DBHandler.SURVIVOR_WINS)),
                c.getInt(c.getColumnIndexOrThrow(DBHandler.SURVIVOR_GAMES_PLAYED)),
                c.getInt(c.getColumnIndexOrThrow(DBHandler.VIRAL_INFECTIONS)),
                c.getInt(c.getColumnIndexOrThrow(DBHandler.VIRAL_GAMES_PLAYED))))
        }

        c.close()
        db.close()
        return players
    }

    fun getPlayer(id: Long): Player {
        val db = dbHandler.readableDatabase
        //query for player using id
        val c: Cursor = db.query(DBHandler.TABLE_NAME, null, "${DBHandler._ID} = ?", arrayOf(id.toString()), null, null, null, null)

        c.moveToFirst()
        //log.d all values of the cursor as msg with tag as testing

        val returnPlayer = Player(
            c.getLong(c.getColumnIndexOrThrow(DBHandler._ID)),
            c.getString(c.getColumnIndexOrThrow(DBHandler.PLAYER_NAME)),
            c.getInt(c.getColumnIndexOrThrow(DBHandler.PLAYER_IMG)),
            c.getInt(c.getColumnIndexOrThrow(DBHandler.SURVIVOR_WINS)),
            c.getInt(c.getColumnIndexOrThrow(DBHandler.SURVIVOR_GAMES_PLAYED)),
            c.getInt(c.getColumnIndexOrThrow(DBHandler.VIRAL_INFECTIONS)),
            c.getInt(c.getColumnIndexOrThrow(DBHandler.VIRAL_GAMES_PLAYED)))

        return returnPlayer
    }

    fun updateSurvivorGamesWon(id: Long) {
        val db = dbHandler.writableDatabase
        val update = "UPDATE ${DBHandler.TABLE_NAME} SET ${DBHandler.SURVIVOR_WINS} = ${DBHandler.SURVIVOR_WINS} + 1 WHERE ${DBHandler._ID} = $id"
        db.execSQL(update)
        db.close()
    }

    fun updateSurvivorGamesPlayed(id: Long) {
        val db = dbHandler.writableDatabase
        val update = "UPDATE ${DBHandler.TABLE_NAME} SET ${DBHandler.SURVIVOR_GAMES_PLAYED} = ${DBHandler.SURVIVOR_GAMES_PLAYED} + 1 WHERE ${DBHandler._ID} = $id"
        db.execSQL(update)
        db.close()
    }

    fun updateViralGamesPlayed(id: Long) {
        val db = dbHandler.writableDatabase
        val update = "UPDATE ${DBHandler.TABLE_NAME} SET ${DBHandler.VIRAL_GAMES_PLAYED} = ${DBHandler.VIRAL_GAMES_PLAYED} + 1 WHERE ${DBHandler._ID} = $id"
        db.execSQL(update)
        db.close()
    }

    fun updatePlayerInfections(id: Long, infections: Int) {
        val db = dbHandler.writableDatabase
        val update = "UPDATE ${DBHandler.TABLE_NAME} SET ${DBHandler.VIRAL_INFECTIONS} = ${DBHandler.VIRAL_INFECTIONS} + $infections WHERE ${DBHandler._ID} = $id"
        db.execSQL(update)
        db.close()
    }

    fun deletePlayer(player: Player) {
        Log.v("TEST", "Deleting Player with ID: ${player.playerID}")
        val db = dbHandler.writableDatabase
        db.delete(DBHandler.TABLE_NAME, "${DBHandler._ID} = ?", arrayOf(player.playerID.toString()))
        db.close()
    }
}