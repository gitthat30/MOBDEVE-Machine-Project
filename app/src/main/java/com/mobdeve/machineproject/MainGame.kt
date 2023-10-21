package com.mobdeve.machineproject

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class MainGame : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.survivor_layout)

        val randomEventButton = findViewById<Button>(R.id.btnRandomEvent)
        randomEventButton.setOnClickListener {
            randomEventButton.isClickable = false
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.random_event)
            dialog.setCanceledOnTouchOutside(true)
            dialog.show()
            randomEventButton.postDelayed({
                randomEventButton.isClickable = true
            }, 1000)
        }

        val escapeButton = findViewById<Button>(R.id.btnEscape)
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