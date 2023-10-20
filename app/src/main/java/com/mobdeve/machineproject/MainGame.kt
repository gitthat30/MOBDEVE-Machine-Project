package com.mobdeve.machineproject

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class MainGame : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.turn_layout_roll)
        val randomEventButton = findViewById<Button>(R.id.btnRandomEvent)
        randomEventButton.setOnClickListener {
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.random_event)
            dialog.setCanceledOnTouchOutside(true)
            dialog.show()
        }
        val escapeButton = findViewById<Button>(R.id.btnEndTurn)
        escapeButton.setOnClickListener {
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.player_escape)
            dialog.setCanceledOnTouchOutside(true)
            dialog.show()
        }
    }
}