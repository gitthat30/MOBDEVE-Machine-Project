package com.mobdeve.machineproject

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.machineproject.Model.EventHelper
import com.mobdeve.machineproject.PlayerTurnRecycler.PlayerTurnAdapter
import com.mobdeve.machineproject.SQL.DBHandler
import com.mobdeve.machineproject.SQL.PlayerDatabase

class MainGame : ComponentActivity() {
    var diceRolled: Boolean = false
    private lateinit var randomEventButton: Button
    private lateinit var rollDiceButton: Button
    private lateinit var escapeButton: Button
    private lateinit var enterButton: Button

    private lateinit var currentTurnImg: ImageView
    private lateinit var currentTurnName: TextView

    private lateinit var playerTurnAdapter: PlayerTurnAdapter
    private lateinit var playerTurnRecycler: RecyclerView
    private lateinit var players: ArrayList<HashMap<String, Any>>

    private lateinit var playerDatabase: PlayerDatabase
    private lateinit var dbHandler: DBHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.turn_layout)

        dbHandler = DBHandler(this)
        playerDatabase = PlayerDatabase(applicationContext)

        randomEventButton = findViewById(R.id.btnRandomEvent)
        rollDiceButton = findViewById(R.id.btnRollDice)
        escapeButton  = findViewById(R.id.btnEscape)
        enterButton = findViewById(R.id.btnEnterHouse)

        currentTurnImg = findViewById(R.id.currentTurn_img)
        currentTurnName = findViewById(R.id.currentTurn_name)

        intializeListeners()

//        playerIndices = hashMapOf<String, Long>()
//        val currentPlayerIndices = intent.getLongArrayExtra("PLAYER_INDICES")!!
//
//        for(index in currentPlayerIndices) {
//            playerIndices.putAll(playerDatabase.getPlayer(index))
//        }
    }

    fun intializeListeners() {
        rollDiceButton.setOnClickListener {
            val intent = Intent(this, RollDice::class.java)
            startActivity(intent)
            rollDiceButton.postDelayed({
                rollDiceButton.isClickable = true
            }, 1000)
        }

        enterButton.setOnClickListener {
            val intent = Intent(this, EnterHouse::class.java)
            startActivity(intent)
            enterButton.postDelayed({
                rollDiceButton.isClickable = true
            }, 1000)
        }



        randomEventButton.setOnClickListener {
            randomEventButton.isClickable = false
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.random_event)
            dialog.setCanceledOnTouchOutside(true)
            dialog.show()

            var randomEventName: TextView = dialog.findViewById(R.id.randomEvent_name)
            var randomEventDescription: TextView = dialog.findViewById(R.id.randomEvent_description)

            val randomEvent = EventHelper.getRandomEvent()

            randomEventName.text = randomEvent.eventName
            randomEventDescription.text = randomEvent.eventDescription

            randomEventButton.postDelayed({
                randomEventButton.isClickable = true
            }, 1000)
        }

        escapeButton.setOnClickListener {
            escapeButton.isClickable = false
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.player_escape)
            dialog.setCanceledOnTouchOutside(true)
            dialog.show()
            escapeButton.postDelayed({
                escapeButton.isClickable = true
            }, 1000)
        }
    }
}