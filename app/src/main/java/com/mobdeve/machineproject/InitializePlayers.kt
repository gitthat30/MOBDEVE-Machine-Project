package com.mobdeve.machineproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
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
            startButton.postDelayed({
                startButton.isClickable = true
            }, 1000)
        }
    }
}