package com.mobdeve.machineproject

import android.os.Bundle
import android.widget.Button
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

class RollDice : ComponentActivity() {
    private lateinit var backButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dice_roll)

        backButton = findViewById(R.id.back_to_turn_layout)

        backButton.setOnClickListener {
            finish()
        }
    }
}
