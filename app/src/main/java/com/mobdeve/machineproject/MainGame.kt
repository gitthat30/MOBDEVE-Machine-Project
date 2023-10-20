package com.mobdeve.machineproject

import android.os.Bundle
import androidx.activity.ComponentActivity

class MainGame : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.turn_layout)
    }
}