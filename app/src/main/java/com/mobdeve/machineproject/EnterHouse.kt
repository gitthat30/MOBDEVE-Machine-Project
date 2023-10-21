package com.mobdeve.machineproject

import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton

class EnterHouse : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.house_selection)

        val confirmButton: Button = findViewById(R.id.btn_house_confirm)

        //Button Array
        val houseButtons = arrayOf(
            findViewById<ImageButton>(R.id.btn_house_1),
            findViewById(R.id.btn_house_2),
            findViewById(R.id.btn_house_3),
            findViewById(R.id.btn_house_4),
            findViewById(R.id.btn_house_5),
            findViewById(R.id.btn_house_6),
            findViewById(R.id.btn_house_7),
            findViewById(R.id.btn_house_8),
            findViewById(R.id.btn_house_9),
            findViewById(R.id.btn_house_10),
            findViewById(R.id.btn_house_11),
            findViewById(R.id.btn_house_12),
            findViewById(R.id.btn_house_13),
            findViewById(R.id.btn_house_14),
            findViewById(R.id.btn_house_15),
            findViewById(R.id.btn_house_16),
            findViewById(R.id.btn_house_17)
        )

        val houseButtonNormal = arrayOf(
            R.drawable.house1,
            R.drawable.house2,
            R.drawable.house3,
            R.drawable.house4,
            R.drawable.house5,
            R.drawable.house6,
            R.drawable.house7,
            R.drawable.house8,
            R.drawable.house9,
            R.drawable.house10,
            R.drawable.house11,
            R.drawable.house12,
            R.drawable.house13,
            R.drawable.house14,
            R.drawable.house15,
            R.drawable.house16,
            R.drawable.house17
        )

        val houseButtonSelected = arrayOf(
            R.drawable.house1_selected,
            R.drawable.house2_selected,
            R.drawable.house3_selected,
            R.drawable.house4_selected,
            R.drawable.house5_selected,
            R.drawable.house6_selected,
            R.drawable.house7_selected,
            R.drawable.house8_selected,
            R.drawable.house9_selected,
            R.drawable.house10_selected,
            R.drawable.house11_selected,
            R.drawable.house12_selected,
            R.drawable.house13_selected,
            R.drawable.house14_selected,
            R.drawable.house15_selected,
            R.drawable.house16_selected,
            R.drawable.house17_selected
        )



        confirmButton.isEnabled = false

        confirmButton.setOnClickListener {
            finish()
        }

        for ((index, button) in houseButtons.withIndex()) {
            (button).setOnClickListener {
                (button as ImageButton).setImageResource(houseButtonSelected[index])

                unselectOthers(index, houseButtonNormal, houseButtons)

                confirmButton.isEnabled = true
                confirmButton.setBackgroundResource(R.drawable.end_layout)
                confirmButton.setTextColor(Color.parseColor("#8B2E8F"))
            }
        }

    }

    fun unselectOthers(selected: Int, icons: Array<Int>, houseButtons: Array<ImageButton>, ) {
        for ((index, button) in houseButtons.withIndex()) {
            if(index != selected) {
                button.setImageResource(icons[index])
            }
        }
    }

}