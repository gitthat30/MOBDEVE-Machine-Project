package com.mobdeve.machineproject.Model

object GameSession {
    var players: List<Player> = emptyList()
    var currentRound: Int = 1
    var currentPlayerIndex: Int = 0
    fun initialize(playerList: List<Player>) {
        players = playerList
        currentRound = 1
        currentPlayerIndex = 0
        initializeHouses()
    }

    private fun initializeHouses() {
        for (player in players) {
            player.houses.forEach { it.hasBeenVisited = false } //change this to true for testing randomization
            val randomHouseIndex = (0 until 17).random()
            if(player.isViral != 1){
                player.houses[randomHouseIndex].hasKey = true
            }
        }
    }

    fun startNextTurn() {
        currentPlayerIndex = getNextTurnIndex()
        if (currentPlayerIndex == 0)
            currentRound++
    }
    fun reset() {
        if (players.isNotEmpty()) {
            players = emptyList()
            currentRound = 1
            currentPlayerIndex = 0
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
}



