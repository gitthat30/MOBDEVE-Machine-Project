package com.mobdeve.machineproject.Model

import kotlin.random.Random

class EventHelper {
    companion object {
        private lateinit var events: ArrayList<Event>

        fun initEvents() {
            val events: ArrayList<Event> = ArrayList()

            events.add(Event(
                "Nothing Happens",
                "All is well! Nothing happened thankfully... Carry on.",
                0.1
            ))
            events.add(Event(
                "Fallen Boulder",
                "A boulder has fallen behind you! A boulder piece is placed behind the player",
                0.25
            ))
            events.add(Event(
                "Fallen Tree",
                "A tree has fallen in front of you! A tree piece is placed in front of the player",
                0.25
            ))
            events.add(Event(
                "Rainy Day",
                "Things just aren't going your way huh? -1 dice roll to all player (this lasts until the end of your next turn)",
                0.2
            ))
            events.add(Event(
                "Earthquake",
                "Did you feel a tremor? It's an earthquake, evacuate! All players inside houses are forced to exit",
                .2
            ))

            this.events = events
        }

        fun getRandomEvent(): Event {
            initEvents()
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
}