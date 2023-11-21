package com.mobdeve.machineproject.Model

class Player {
    var playerID: Long
    var name: String
    var playerImg: Int
    var numWins: Int
    var numPlayed: Int
    var isViral: Int = -1
    var houses: MutableList<House> = MutableList(17) { House() }

    constructor(playerID: Long, name: String, playerImg: Int, numWins: Int, numPlayed: Int) {
        this.playerID = playerID
        this.name = name
        this.playerImg = playerImg
        this.numWins = numWins
        this.numPlayed = numPlayed
    }
}