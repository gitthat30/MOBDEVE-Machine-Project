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

        survivorEvents.add(
            Event(
                "Default Survivor Event",
                "This is the default survivor event description. To be added in a bit.",
                1.0
            ))

        viralEvents.add(Event(
            "Default Viral Event",
            "This is the default viral event description. To be added in a bit.",
            1.0
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
                return event
            }
            else {
                randomValue -= event.eventChance
            }
        }
        return events.last()
    }
}