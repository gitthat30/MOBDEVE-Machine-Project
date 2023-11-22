package com.mobdeve.machineproject.Model

class Player {
    var playerID: Long
    var name: String
    var playerImg: Int
    var numSurvivorWins: Int
    var numSurvivorPlayed: Int
    var numViralInfections: Int
    var numViralPlayed: Int
    var isViral: Int = -1
    var houses: MutableList<House> = MutableList(17) { House() }
    var escaped: Boolean = false

    constructor(playerID: Long, name: String, playerImg: Int, numSurvivorWins: Int, numSurvivorPlayed: Int, numViralInfections: Int, numViralPlayed: Int) {
        this.playerID = playerID
        this.name = name
        this.playerImg = playerImg
        this.numSurvivorWins = numSurvivorWins
        this.numSurvivorPlayed = numSurvivorPlayed
        this.numViralInfections = numViralInfections
        this.numViralPlayed = numViralPlayed
    }
}