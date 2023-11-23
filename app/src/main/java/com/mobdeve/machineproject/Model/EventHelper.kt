package com.mobdeve.machineproject.Model

import kotlin.random.Random

class EventHelper() {
    private var randomEvents: ArrayList<Event> = ArrayList()
    private var survivorEvents: ArrayList<Event> = ArrayList()
    private var viralEvents: ArrayList<Event> = ArrayList()

    enum class EventType {
        Random, Survivor, Viral
    }

    init {
        // Adding random events
        randomEvents.add(Event(
            "Nothing Happens",
            "All is well! Nothing happened thankfully... Carry on.",
            0.1
        ))
        randomEvents.add(Event(
            "Fallen Boulder",
            "A boulder has fallen behind you! A boulder piece is placed behind the player.",
            0.25
        ))
        randomEvents.add(Event(
            "Fallen Tree",
            "A tree has fallen in front of you! A tree piece is placed in front of the player.",
            0.25
        ))
        randomEvents.add(Event(
            "Rainy Day",
            "Things just aren't going your way huh? -1 dice roll to all player (this lasts until the end of your next turn).",
            0.2
        ))
        randomEvents.add(Event(
            "Earthquake",
            "Did you feel a tremor? It's an earthquake! We need to evacuate! All players inside houses are forced to exit.",
            .2
        ))

        // Adding survivor events
        survivorEvents.add(Event(
            "Nothing Happens",
            "All is well! Nothing happened thankfully... Carry on.",
            0.4
        ))

        survivorEvents.add(Event(
            "Panic Attack",
            "Need to move! The player moves 5 steps this round.",
            0.2
        ))

        survivorEvents.add(Event(
            "Lucky Day",
            "Things are looking up! Shuffle the discard pile and draw one card. If the discard pile is empty, do not draw.",
            0.2
        ))

        survivorEvents.add(Event(
            "Muscle Cramps",
            "Your muscles cramp and can't move very far. Lose one dice roll in the next round.",
            0.2
        ))

        // Adding viral events
        viralEvents.add(Event(
            "Nothing Happens",
            "All is well! Nothing happened thankfully... Carry on.",
            0.4
        ))

        viralEvents.add(Event(
            "Random Distraction",
            "Need to move! The player moves 5 steps this round..",
            0.4
        ))

        viralEvents.add(Event(
            "Rat Snack",
            "Yummy! The player obtains a rat nearby as an item. This can be used to add an additional roll (maximum of 2 at a time).",
            0.2
        ))
    }

    fun getRandomEvent(type: EventType): Event {
        val events = when(type) {
            EventType.Random -> randomEvents
            EventType.Survivor -> survivorEvents
            EventType.Viral -> viralEvents
        }

        val totalProbability = events.sumOf { it.eventChance }
        var randomValue = Random.nextDouble(0.0, totalProbability)

        for(event in events) {
            if(randomValue < event.eventChance) {
                if (event.eventName == "Rainy Day") {
                    GameSession.rainValue = GameSession.players.count{!it.escaped} //total rain turns is remaining player count
                }
                else if (event.eventName == "Muscle Cramps") {
                    GameSession.getCurrentPlayer().muscleCramps = true
                }
                return event
            }
            else {
                randomValue -= event.eventChance
            }
        }
        return events.last()
    }
}