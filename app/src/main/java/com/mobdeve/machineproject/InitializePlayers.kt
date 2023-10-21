package com.mobdeve.machineproject

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.ComponentActivity

class InitializePlayers : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.initialize_players_layout)

        val startButton = findViewById<Button>(R.id.btnStart)
        startButton.setOnClickListener {
            startButton.isClickable = false
            val intent = Intent(this, MainGame::class.java)
            startActivity(intent)
            finish()
            startButton.postDelayed({
                startButton.isClickable = true
            }, 1000)
        }
        val linearLayoutIds = listOf(
            R.id.llViral,
            R.id.llSurvivor1,
            R.id.llSurvivor2,
            R.id.llSurvivor3,
            R.id.llSurvivor4
        )

        for (linearLayoutId in linearLayoutIds) {
            val llPlayers = findViewById<LinearLayout>(linearLayoutId)
            llPlayers.setOnClickListener {
                llPlayers.isClickable = false
                val intent = Intent(this, SelectPlayer::class.java)
                startActivity(intent)
                startButton.postDelayed({
                    llPlayers.isClickable = true
                }, 1000)
            }
        }
    }
}