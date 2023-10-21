package com.mobdeve.machineproject

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts

class MainGame : ComponentActivity() {
    var diceRolled: Boolean = false
    private lateinit var randomEventButton: Button
    private lateinit var rollDiceButton: Button
    private lateinit var escapeButton: Button
    private lateinit var enterButton: Button




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.survivor_layout)

        randomEventButton = findViewById(R.id.btnRandomEvent)
        rollDiceButton = findViewById(R.id.btnRollDice)
        escapeButton  = findViewById(R.id.btnEscape)
        enterButton = findViewById(R.id.btnEnterHouse)

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
            randomEventButton.postDelayed({
                randomEventButton.isClickable = true
            }, 1000)
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
    }
}