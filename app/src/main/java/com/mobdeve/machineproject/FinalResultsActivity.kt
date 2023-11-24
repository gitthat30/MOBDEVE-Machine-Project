package com.mobdeve.machineproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewParent
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.mobdeve.machineproject.Model.GameSession
import com.mobdeve.machineproject.Model.Player
import com.mobdeve.machineproject.SQL.PlayerDatabase

class FinalResultsActivity : ComponentActivity() {
    private val escapees = GameSession.getEscapees()
    private val died = GameSession.getDiedToInfection()

    private lateinit var backToMain: Button

    private lateinit var viralImg: ImageView
    private lateinit var viralName: TextView
    private lateinit var gameInfections: TextView
    private lateinit var totalInfections: TextView

    private lateinit var escapeesNone: TextView
    private lateinit var diedNone: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.results_layout)

        val playerDatabase = PlayerDatabase(applicationContext)

        backToMain = findViewById(R.id.btnBackToMain)
        viralImg = findViewById(R.id.viral_end_img)
        viralName = findViewById(R.id.viral_end_name)
        gameInfections = findViewById(R.id.game_infections_results)
        totalInfections = findViewById(R.id.total_infections_results)

        val escapeesIds: LinkedHashMap<Int, Int> = linkedMapOf(
            R.id.players_escaped_1_tv to R.id.players_escaped_1_img,
            R.id.players_escaped_2_tv to R.id.players_escaped_2_img,
            R.id.players_escaped_3_tv to R.id.players_escaped_3_img,
            R.id.players_escaped_4_tv to R.id.players_escaped_4_img,
        )

        val diedIds: LinkedHashMap<Int, Int> = linkedMapOf(
            R.id.players_died_1_tv to R.id.players_died_1_img,
            R.id.players_died_2_tv to R.id.players_died_2_img,
            R.id.players_died_3_tv to R.id.players_died_3_img,
            R.id.players_died_4_tv to R.id.players_died_4_img,
        )

        setPlayerBubbles(escapees, escapeesIds)
        setPlayerBubbles(died, diedIds)

        if(escapees.isEmpty()) {
            escapeesNone = findViewById(R.id.escapees_none)
            escapeesNone.visibility = View.VISIBLE
        }

        if(died.isEmpty()) {
            diedNone = findViewById(R.id.died_none)
            diedNone.visibility = View.VISIBLE
        }

        viralImg.setImageResource(R.drawable.viral2)
        viralName.text = GameSession.getViral().name
        gameInfections.text = "${died.size}"
        totalInfections.text = "${GameSession.getViral().numViralInfections + died.size}"

        for(player in GameSession.players) {
            if(player.isViral == 1) {
                playerDatabase.updateViralGamesPlayed(player.playerID)
                playerDatabase.updatePlayerInfections(player.playerID, died.size)
            }
            else {
                playerDatabase.updateSurvivorGamesPlayed(player.playerID)
                if(player.escaped)
                    playerDatabase.updateSurvivorGamesWon(player.playerID)
            }
        }

        backToMain.setOnClickListener {
            val intent = Intent(this, TitleScreen::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun setPlayerBubbles(players: List<Player>, ids: LinkedHashMap<Int, Int>) {
        // go thru players and populate bubbles, once reaching the end of list, make the rest of the bubbles GONE
        for ((index, player) in players.withIndex()) {
            val tv = findViewById<TextView>(ids.keys.toList()[index])
            tv.text = player.name

            val img = findViewById<ImageView>(ids.values.toList()[index])
            img.setImageResource(player.playerImg)
        }

        for (i in players.size until ids.size) {
            val child = findViewById<TextView>(ids.keys.toList()[i])
            val parent = child.parent as View
            parent.visibility = View.GONE
        }
    }

}