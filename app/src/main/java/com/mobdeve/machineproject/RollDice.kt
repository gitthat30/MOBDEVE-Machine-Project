package com.mobdeve.machineproject

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mobdeve.machineproject.ui.ui.theme.MachineProjectTheme
import pl.droidsonroids.gif.GifImageView

class RollDice : ComponentActivity() {
    private lateinit var backButton: Button
    private lateinit var minusButton: ImageButton
    private lateinit var plusButton: ImageButton
    private lateinit var diceCountTxt: TextView
    private lateinit var diceGif: GifImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dice_roll)

        val diceGifs = arrayOf(
            R.drawable.dice1,
            R.drawable.dice2,
            R.drawable.dice3,
            R.drawable.dice4
        )

        var diceCount = 1


        backButton = findViewById(R.id.back_to_turn_layout)
        minusButton = findViewById(R.id.minus_btn)
        plusButton = findViewById(R.id.plus_btn)
        diceCountTxt = findViewById(R.id.dice_count_txt)
        diceGif = findViewById(R.id.dice_gif)



        backButton.setOnClickListener {
            finish()
        }

        minusButton.setOnClickListener {
            if (diceCount > 1) {
                diceCount--
                diceCountTxt.setText(diceCount.toString())
                diceGif.setBackgroundResource(diceGifs[diceCount - 1])
            }
        }

        plusButton.setOnClickListener {
            if (diceCount < 4) {
                diceCount++
                diceCountTxt.setText(diceCount.toString())
                diceGif.setBackgroundResource(diceGifs[diceCount - 1])
            }
        }
    }

    fun normalizeResolution(diceCount: Int) {

    }
}
