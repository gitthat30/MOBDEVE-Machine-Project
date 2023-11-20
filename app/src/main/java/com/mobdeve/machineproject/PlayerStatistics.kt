package com.mobdeve.machineproject

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.machineproject.Model.Player
import com.mobdeve.machineproject.PlayerStatsRecycler.PlayerStatsAdapter
import com.mobdeve.machineproject.SQL.DBHandler
import com.mobdeve.machineproject.SQL.PlayerDatabase
import com.mobdeve.machineproject.SelectPlayerRecycler.SelectPlayerAdapter

class PlayerStatistics : ComponentActivity() {
    private lateinit var playerStatsAdapter: PlayerStatsAdapter
    private lateinit var playerStatsRecyclerView: RecyclerView

    private lateinit var playerDatabase: PlayerDatabase
    private lateinit var dbHandler: DBHandler
    private lateinit var players: ArrayList<Player>

    private val displayConfirmPopup = {position: Int ->
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.confirm_popup)
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()

        val yesButton = dialog.findViewById<Button>(R.id.confirmPopup_yes)
        val cancelButton = dialog.findViewById<Button>(R.id.confirmPopup_cancel)

        yesButton.setOnClickListener {
            playerStatsAdapter.deletePlayerStat(position)
            dialog.dismiss()
        }
        cancelButton.setOnClickListener {
            dialog.dismiss()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.player_stats)

        dbHandler = DBHandler(this)
        playerDatabase = PlayerDatabase(applicationContext)
        players = playerDatabase.getAllPlayers()

        for (p in players) {
            Log.d("test", "onCreate: ${p.name}")
        }

        playerStatsRecyclerView = findViewById(R.id.playerStatsRecycler)
        playerStatsAdapter = PlayerStatsAdapter(players, this, displayConfirmPopup)
        playerStatsRecyclerView.adapter = playerStatsAdapter
        playerStatsRecyclerView.layoutManager = LinearLayoutManager(this)
    }


}