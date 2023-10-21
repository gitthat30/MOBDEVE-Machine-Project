package com.mobdeve.machineproject

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.ComponentActivity

class TitleScreen : ComponentActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.title_screen)
            val newGameButton = findViewById<ImageButton>(R.id.btnNewGame)
            newGameButton.setOnClickListener {
                val intent = Intent(this, InitializePlayers::class.java)
                startActivity(intent)
                newGameButton.postDelayed({
                    newGameButton.isClickable = true
                }, 1000)
            }
            val continueButton = findViewById<ImageButton>(R.id.btnContinue)
            continueButton.setOnClickListener {
                continueButton.isClickable = false
                val intent = Intent(this, MainGame::class.java)
                startActivity(intent)
                continueButton.postDelayed({
                    continueButton.isClickable = true
                }, 1000)
            }
        }
    }