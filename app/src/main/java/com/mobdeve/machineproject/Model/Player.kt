package com.mobdeve.machineproject.Model

class Player {
    var name: String
    var playerImg: Int
    var numViral: Int
    var numSurvivor: Int
    var numSurvivorWins: Int
    var numInfected: Int

    constructor(name: String, playerImg: Int, numViral: Int, numSurvivor: Int, numSurvivorWins: Int, numInfected: Int) {
        this.name = name
        this.playerImg = playerImg
        this.numViral = numViral
        this.numSurvivor = numSurvivor
        this.numSurvivorWins = numSurvivorWins
        this.numInfected = numInfected
    }
}