package com.mobdeve.machineproject

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
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
    private lateinit var column1: LinearLayout
    private lateinit var column2: LinearLayout
    private lateinit var dice1: ImageView
    private lateinit var dice2: ImageView
    private lateinit var dice3: ImageView
    private lateinit var dice4: ImageView

    private lateinit var backButton: Button
    private lateinit var minusButton: ImageButton
    private lateinit var plusButton: ImageButton

    private var diceNum: Int = 1
    private var diceValues: Array<Int> = arrayOf(0, 0, 0, 0)
    private var diceImages: Array<Int> = arrayOf(
        R.drawable.xml_dice1,
        R.drawable.xml_dice2,
        R.drawable.xml_dice3,
        R.drawable.xml_dice4,
        R.drawable.xml_dice5,
        R.drawable.xml_dice6)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dice_roll)

        initViews()
        initLisenters()
        updateClickable()
        initDiceValues()
        updateDiceImages()
        updateDiceCount()

    }

    fun initViews() {
        column1 = findViewById(R.id.dice_column1)
        column2 = findViewById(R.id.dice_column2)

        dice1 = column1.findViewById(R.id.dice1_img)
        dice2 = column1.findViewById(R.id.dice2_img)
        dice3 = column2.findViewById(R.id.dice3_img)
        dice4 = column2.findViewById(R.id.dice4_img)

        dice2.visibility = View.GONE
        dice3.visibility = View.GONE
        dice4.visibility = View.GONE

        backButton = findViewById(R.id.back_to_turn_layout)
        minusButton = findViewById(R.id.minus_btn)
        plusButton = findViewById(R.id.plus_btn)
    }

    fun initLisenters() {
        minusButton.setOnClickListener {
            diceNum--
            updateClickable()
            updateDiceCount()
        }

        plusButton.setOnClickListener {
            diceNum++
            updateClickable()
            updateDiceCount()
        }
    }

    fun updateClickable() {
        minusButton.isClickable = true
        plusButton.isClickable = true

        if(diceNum == 1) {
            minusButton.isClickable = false
        }
        else if (diceNum == 4) {
            plusButton.isClickable = false
        }
    }

    fun updateDiceImages() {
        dice1.setImageResource(diceImages[diceValues[0]-1])
        dice2.setImageResource(diceImages[diceValues[1]-1])
        dice3.setImageResource(diceImages[diceValues[2]-1])
        dice4.setImageResource(diceImages[diceValues[3]-1])
    }
    fun updateDiceCount() {
        dice1.visibility = View.GONE
        dice2.visibility = View.GONE
        dice3.visibility = View.GONE
        dice4.visibility = View.GONE

        when(diceNum) {
            1 -> {
                dice1.visibility = View.VISIBLE
            }
            2 -> {
                dice1.visibility = View.VISIBLE
                dice2.visibility = View.VISIBLE
            }
            3 -> {
                dice1.visibility = View.VISIBLE
                dice2.visibility = View.VISIBLE
                dice3.visibility = View.VISIBLE
            }
            4 -> {
                dice1.visibility = View.VISIBLE
                dice2.visibility = View.VISIBLE
                dice3.visibility = View.VISIBLE
                dice4.visibility = View.VISIBLE
            }
        }
    }

    fun initDiceValues() {
        diceValues = arrayOf(
            (1..6).random(),
            (1..6).random(),
            (1..6).random(),
            (1..6).random()
        )
    }
}
