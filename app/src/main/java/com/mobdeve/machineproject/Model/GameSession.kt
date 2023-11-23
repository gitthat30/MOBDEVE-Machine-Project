package com.mobdeve.machineproject.Model
import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

object GameSession {
    var players: List<Player> = emptyList()
    var currentRound: Int = 1
    var currentPlayerIndex: Int = 0
    var sessionConcluded: Boolean = false
    var rainValue: Int = 0
    fun initialize(playerList: List<Player>, context: Context) {
        players = playerList
        currentRound = 1
        currentPlayerIndex = 0
        sessionConcluded = false
        rainValue = 0
        initializeHouses()
        saveGame(context)
    }
    fun initializeJson(context: Context) {
        val sharedPreferences = context.getSharedPreferences("VI_Preferences", Context.MODE_PRIVATE)
        val gson = Gson()
        val playersJson = sharedPreferences.getString("players", null)
        val currentRound = sharedPreferences.getInt("currentRound", 1)
        val currentPlayerIndex = sharedPreferences.getInt("currentPlayerIndex", 0)
        val sessionConcluded = sharedPreferences.getBoolean("sessionConcluded", false)
        val rainValue = sharedPreferences.getInt("rainValue", 0)

        if (playersJson != null) {
            val playerType = object : TypeToken<List<Player>>() {}.type
            val players: List<Player> = gson.fromJson(playersJson, playerType)

            GameSession.players = players
            GameSession.currentRound = currentRound
            GameSession.currentPlayerIndex = currentPlayerIndex
            GameSession.sessionConcluded = sessionConcluded
            GameSession.rainValue = rainValue
        }
    }

    fun initializeHouses() {
        for (player in players) {
            player.houses.forEach { it.hasBeenVisited = false } //change this to true for testing randomization
            val randomHouseIndex = (0 until 17).random()
            if(player.isViral != 1){
                player.houses[randomHouseIndex].hasKey = true
            }
        }
    }
    fun getKeyLocation(player: Player): Int{
        return player.houses.indexOfFirst{it.hasKey}
    }

    fun startNextTurn(context: Context) {
        currentPlayerIndex = getNextTurnIndex()
        if (currentPlayerIndex == 0)
            currentRound++
        while (players[currentPlayerIndex].escaped) { //added to skip escaped players
            currentPlayerIndex = getNextTurnIndex()
            if (currentPlayerIndex == 0)
                currentRound++
        }
        if(rainValue>0){
            rainValue--
        }
        saveGame(context)
    }
    fun reset(context: Context) {
        if (players.isNotEmpty()) {
            players = emptyList()
            currentRound = 1
            currentPlayerIndex = 0
            sessionConcluded = false
            rainValue = 0
        }
        saveGame(context)
    }

    fun getNextTurnIndex(): Int {
        var nextIndex: Int = currentPlayerIndex + 1
        if (nextIndex >= players.size)
            nextIndex = 0

        return nextIndex
    }

    fun getCurrentPlayer(): Player {
        return players[currentPlayerIndex]
    }

    fun getNextPlayer(): Player {
        var index = getNextTurnIndex()
        var nextPlayer = players[index]
        while (nextPlayer.escaped) { //added to skip escaped players
            index++
            if (index >= players.size){
                index = 0
            }
            nextPlayer = players[index]
        }
        return nextPlayer
    }

    fun getViral(): Player {
        return players.filter { it.isViral == 1 }[0]
    }

    fun escapeCurrentPlayer() {
        players[currentPlayerIndex].escaped = true
    }
    fun allPlayersEscaped(): Boolean {
        return players.filter {it.isViral != 1}.all { it.escaped }
    }
    fun endGame(context: Context) {
        sessionConcluded = true
        val sharedPreferences = context.getSharedPreferences("VI_Preferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("sessionConcluded", sessionConcluded)
        editor.apply()
    }
    private fun saveGame(context: Context) {
        val gson = Gson()
        val playersJson = gson.toJson(players)
        val sharedPreferences = context.getSharedPreferences("VI_Preferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        //Log.d("PlayersJsonLog", playersJson)

        editor.putString("players", playersJson)
        editor.putInt("currentRound", currentRound)
        editor.putInt("currentPlayerIndex", currentPlayerIndex)
        editor.putBoolean("sessionConcluded", sessionConcluded)
        editor.putInt("rainValue", rainValue)
        editor.apply()
    }

    fun getEscapees(): List<Player> {
        return players.filter { it.escaped }
    }

    fun getDiedToInfection(): List<Player> {
        return players.filter { !it.escaped && it.isViral != 1 }
    }
}



