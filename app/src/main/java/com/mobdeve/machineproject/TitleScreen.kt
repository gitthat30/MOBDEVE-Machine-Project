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
            val continueButton = findViewById<Button>(R.id.btnContinue)
            val playerStatsButton = findViewById<Button>(R.id.btnPlayerStats)

            val tsButtons = listOf(newGameButton, continueButton, playerStatsButton)

            newGameButton.setOnClickListener {
                val intent = Intent(this, InitializePlayers::class.java)
                for (button in tsButtons) {
                    button.isClickable = false
                }
                startActivity(intent)
                newGameButton.postDelayed({
                    for (button in tsButtons) {
                        button.isClickable = true
                    }
                }, 1000)
            }

            continueButton.setOnClickListener {
                for (button in tsButtons) {
                    button.isClickable = false
                }
                val intent = Intent(this, MainGame::class.java)
                startActivity(intent)
                continueButton.postDelayed({
                    for (button in tsButtons) {
                        button.isClickable = true
                    }
                }, 1000)
            }

            playerStatsButton.setOnClickListener {
                val intent = Intent(this, PlayerStatistics::class.java)
                startActivity(intent)
                for (button in tsButtons) {
                    button.isClickable = false
                }
                playerStatsButton.postDelayed({
                    for (button in tsButtons) {
                        button.isClickable = true
                    }
                }, 1000)
            }
        }
    }