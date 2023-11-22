package com.mobdeve.machineproject

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.ComponentActivity
import com.google.gson.Gson
import com.mobdeve.machineproject.Model.GameSession

class TitleScreen : ComponentActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.title_screen)
            val newGameButton = findViewById<Button>(R.id.btnNewGame)
            val continueButton = findViewById<Button>(R.id.btnContinue)
            val playerStatsButton = findViewById<Button>(R.id.btnPlayerStats)
            val tsButtons = listOf(newGameButton, continueButton, playerStatsButton)
            val sharedPreferences = getSharedPreferences("VI_Preferences", Context.MODE_PRIVATE)
            val playerJsonString = sharedPreferences.getString("players", null)

            if (playerJsonString != null) {
                GameSession.initializeJson(this)
            }
            
            if (GameSession.sessionConcluded || GameSession.players.isEmpty()) {
                continueButton.isEnabled = false
                continueButton.alpha = 0.5f
            } else {
                continueButton.isEnabled = true
                continueButton.alpha = 1.0f
            }

            newGameButton.setOnClickListener {
                val intent = Intent(this, InitializePlayers::class.java)
                for (button in tsButtons) {
                    button.isClickable = false
                }
                if (GameSession.players.isNotEmpty() && !GameSession.sessionConcluded) {
                    val confirmDialog = AlertDialog.Builder(this)
                        .setTitle("Confirm New Game")
                        .setMessage("Starting a new game will delete the ongoing game session, are you sure you want to continue?")
                        .setPositiveButton("Yes") { _, _ ->
                            GameSession.reset(this)
                            startActivity(intent)
                        }
                        .setNegativeButton("No") { _, _ ->
                            // do nothing
                        }
                        .create()
                    confirmDialog.show()
                } else {
                    startActivity(intent)
                }
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
        override fun onResume() {
            super.onResume()
            val continueButton = findViewById<Button>(R.id.btnContinue)
            if (GameSession.sessionConcluded || GameSession.players.isEmpty()) {
                continueButton.isEnabled = false
                continueButton.alpha = 0.5f
            } else {
                continueButton.isEnabled = true
                continueButton.alpha = 1.0f
            }
        }
    }