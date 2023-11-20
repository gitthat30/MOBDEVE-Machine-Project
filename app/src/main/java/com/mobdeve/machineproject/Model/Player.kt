package com.mobdeve.machineproject.Model

class Player {
    var playerID: Long
    var name: String
    var playerImg: Int
    var numWins: Int
    var numPlayed: Int

    constructor(playerID: Long, name: String, playerImg: Int, numWins: Int, numPlayed: Int) {
        this.playerID = playerID
        this.name = name
        this.playerImg = playerImg
        this.numWins = numWins
        this.numPlayed = numPlayed
    }
}