package com.mobdeve.machineproject.Model

object GameSession {
    var players: List<Player> = emptyList()
    private var currentRound: Int = 1
    var currentPlayerIndex: Int = 0
    fun initialize(playerList: List<Player>) {
        players = playerList
        currentRound = 1
        currentPlayerIndex = 0
    }
    fun startNextTurn() {
        currentPlayerIndex++
        if (currentPlayerIndex >= players.size) {
            currentPlayerIndex = 0
            currentRound++
        }
    }
    fun reset() {
        if (players.isNotEmpty()) {
            players = emptyList()
            currentRound = 1
            currentPlayerIndex = 0
        }
    }
}