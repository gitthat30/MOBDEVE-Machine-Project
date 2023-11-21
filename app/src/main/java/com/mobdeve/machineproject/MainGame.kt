package com.mobdeve.machineproject

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.machineproject.Model.EventHelper
import com.mobdeve.machineproject.Model.GameSession
import com.mobdeve.machineproject.PlayerTurnRecycler.PlayerTurnAdapter
import com.mobdeve.machineproject.SQL.DBHandler
import com.mobdeve.machineproject.SQL.PlayerDatabase

class MainGame : ComponentActivity() {
    var diceRolled: Boolean = false
    private lateinit var randomEventButton: Button
    private lateinit var rollDiceButton: Button
    private lateinit var escapeButton: Button
    private lateinit var enterButton: Button
    private lateinit var endTurnButton: Button
    private lateinit var skipTurnButton: Button

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
        endTurnButton = findViewById(R.id.btnEndTurn)
        skipTurnButton = findViewById(R.id.btnSkipTurn)

        val ivCurrentPlayer = findViewById<ImageView>(R.id.iv_currentPlayer)
        val ivNextPlayer1 = findViewById<ImageView>(R.id.iv_nextPlayer1)
        val ivNextPlayer2 = findViewById<ImageView>(R.id.iv_nextPlayer2)
        val ivNextPlayer3 = findViewById<ImageView>(R.id.iv_nextPlayer3)
        val ivNextPlayer4 = findViewById<ImageView>(R.id.iv_nextPlayer4)
        val tvRoundNumber = findViewById<TextView>(R.id.tv_roundNumber)


        val textViewIds = arrayOf(
            R.id.tv_currentPlayer,
            R.id.tv_nextPlayer1,
            R.id.tv_nextPlayer2,
            R.id.tv_nextPlayer3,
            R.id.tv_nextPlayer4
        )

        for (i in 0 until GameSession.players.size) {
            val textView = findViewById<TextView>(textViewIds[i])
            val playerIndex = (GameSession.currentPlayerIndex + i) % GameSession.players.size
            textView.text = GameSession.players[playerIndex].name
        }
        for (i in GameSession.players.size until textViewIds.size) {
            val textView = findViewById<TextView>(textViewIds[i])
            val parentLayout = textView.parent as? LinearLayout
            parentLayout?.visibility = View.GONE
        }

        tvRoundNumber.text = "Round ${GameSession.currentRound}"

        intializeListeners()
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

            val randomEventName: TextView = dialog.findViewById(R.id.randomEvent_name)
            val randomEventDescription: TextView = dialog.findViewById(R.id.randomEvent_description)

            val eventHelper = EventHelper()
            val randomEvent = eventHelper.getRandomEvent(EventHelper.EventType.Random)

            randomEventName.text = randomEvent.eventName
            randomEventDescription.text = randomEvent.eventDescription

            randomEventButton.postDelayed({
                randomEventButton.isClickable = true
            }, 1000)
        }

        endTurnButton.setOnClickListener {
            escapeButton.isClickable = false
            escapeButton.postDelayed({
                escapeButton.isClickable = true
            }, 1000)
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.end_turn)
            dialog.setCanceledOnTouchOutside(true)
            dialog.show()

            val turnEndName: TextView = dialog.findViewById(R.id.turnEnd_name)
            val turnEndImg: ImageView = dialog.findViewById(R.id.turnEnd_img)

            // Temporary condition since players is always empty for now when continuing game
            if(GameSession.players.isNotEmpty()) {
                turnEndName.text = GameSession.getNextPlayer().name
                turnEndImg.setImageResource(GameSession.getNextPlayer().playerImg)
            }

            dialog.setOnDismissListener {
                GameSession.startNextTurn()
                finish()
                startActivity(Intent(this, javaClass))
            }
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

        skipTurnButton.setOnClickListener {
            escapeButton.isClickable = false
            skipTurnButton.postDelayed({
                escapeButton.isClickable = true
            }, 1000)
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.random_event)
            dialog.setCanceledOnTouchOutside(true)
            dialog.show()

            val randomEventName: TextView = dialog.findViewById(R.id.randomEvent_name)
            val randomEventDescription: TextView = dialog.findViewById(R.id.randomEvent_description)

            val eventHelper = EventHelper()
            var eventType = EventHelper.EventType.Survivor

            // Can remove first condition once players isn't always empty
            if(GameSession.players.isNotEmpty() && GameSession.getCurrentPlayer().isViral == 1) {
                eventType = EventHelper.EventType.Viral
            }
            val randomEvent = eventHelper.getRandomEvent(eventType)

            randomEventName.text = randomEvent.eventName
            randomEventDescription.text = randomEvent.eventDescription

            dialog.setOnDismissListener {
                endTurnButton.performClick()
            }
        }
    }
}