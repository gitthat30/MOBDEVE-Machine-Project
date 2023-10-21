package com.mobdeve.machineproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.ComponentActivity

class TitleScreen : ComponentActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.title_screen)
            val newGameButton = findViewById<Button>(R.id.btnNewGame)
            newGameButton.setOnClickListener {
                val intent = Intent(this, InitializePlayers::class.java)
                startActivity(intent)
                newGameButton.postDelayed({
                    newGameButton.isClickable = true
                }, 1000)
            }
            val continueButton = findViewById<Button>(R.id.btnContinue)
            continueButton.setOnClickListener {
                continueButton.isClickable = false
                val intent = Intent(this, MainGame::class.java)
                startActivity(intent)
                continueButton.postDelayed({
                    continueButton.isClickable = true
                }, 1000)
            }
            val playerStatsButton = findViewById<Button>(R.id.btnPlayerStats)
            playerStatsButton.setOnClickListener {
                val intent = Intent(this, PlayerStatistics::class.java)
                startActivity(intent)
                newGameButton.postDelayed({
                    newGameButton.isClickable = true
                }, 1000)
            }
        }
    }