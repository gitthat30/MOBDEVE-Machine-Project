package com.mobdeve.machineproject

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.ComponentActivity
import com.mobdeve.machineproject.SQL.DBHandler
import com.mobdeve.machineproject.SQL.PlayerDatabase

class InitializePlayers : ComponentActivity() {
    companion object {
        val PLAYER_ID_KEY = "PLAYER_ID_KEY"
        val CLICKED_KEY = "CLICKED_KEY"

        val VIRAL_CLICKED = "VIRAL_CLICKED"
        val PLAYER_CLICKED = "PLAYER_CLICKED"
        val PLAYER_NUMBER = "PLAYER_NUMBER"
    }

    private var viral_ID: Long = -1


    private lateinit var viral_LL: LinearLayout

    private lateinit var playerDatabase: PlayerDatabase

    private val getPlayerIDActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            val playerID = result.data!!.getLongExtra(PLAYER_ID_KEY, -1)
            //Log playerID with tag Testing Result
            Log.d("Testing Result", "onActivityResult: $playerID")

            if(result.data!!.getStringExtra(CLICKED_KEY) == VIRAL_CLICKED) {
                Log.d("Testing Result", "onActivityResult: VIRAL CLICKED")
                val viral_txt: TextView = viral_LL.findViewById(R.id.txtViral)

                val data: HashMap<String, Any> = playerDatabase.getPlayer(playerID)

                viral_txt.text = data[DBHandler.PLAYER_NAME].toString()
                viral_ID = data[DBHandler._ID] as Long
            }


        }
        else if (result.resultCode == RESULT_CANCELED) {
            Log.d("Testing Result", "Nothing")
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.initialize_players_layout)

        playerDatabase = PlayerDatabase(applicationContext)

        val startButton = findViewById<Button>(R.id.btnStart)
        startButton.setOnClickListener {
            startButton.isClickable = false
            val intent = Intent(this, MainGame::class.java)
            startActivity(intent)
            finish()
            startButton.postDelayed({
                startButton.isClickable = true
            }, 1000)
        }
        viral_LL = findViewById<LinearLayout>(R.id.llViral)

        viral_LL.setOnClickListener {
            val intent = Intent(this, SelectPlayer::class.java)
            intent.putExtra(CLICKED_KEY, VIRAL_CLICKED)
            getPlayerIDActivityLauncher.launch(intent)
        }
    }
}