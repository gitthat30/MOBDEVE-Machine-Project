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
    fun initialize(playerList: List<Player>, context: Context) {
        players = playerList
        currentRound = 1
        currentPlayerIndex = 0
        sessionConcluded = false
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

        if (playersJson != null) {
            val playerType = object : TypeToken<List<Player>>() {}.type
            val players: List<Player> = gson.fromJson(playersJson, playerType)

            GameSession.players = players
            GameSession.currentRound = currentRound
            GameSession.currentPlayerIndex = currentPlayerIndex
            GameSession.sessionConcluded = sessionConcluded
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

    fun startNextTurn(context: Context) {
        currentPlayerIndex = getNextTurnIndex()
        if (currentPlayerIndex == 0)
            currentRound++
        while (players[currentPlayerIndex].escaped) { //added to skip escaped players
            currentPlayerIndex = getNextTurnIndex()
            if (currentPlayerIndex == 0)
                currentRound++
        }
        saveGame(context)
    }
    fun reset() {
        if (players.isNotEmpty()) {
            players = emptyList()
            currentRound = 1
            currentPlayerIndex = 0
            sessionConcluded = false
        }
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
        return players[getNextTurnIndex()]
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
        editor.apply()
    }
}



