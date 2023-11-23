package com.mobdeve.machineproject

import android.app.AlertDialog
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
import com.mobdeve.machineproject.Model.GameSession
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
        val confirmDialog = AlertDialog.Builder(this)
        .setTitle("Confirm Player Deletion")
        .setMessage("Are you sure you want to delete this player?")
        .setPositiveButton("Yes") { _, _ ->
            if (GameSession.players.isNotEmpty() && !GameSession.sessionConcluded){
                AlertDialog.Builder(this)
                .setTitle("Cannot Delete Player")
                .setMessage("You cannot delete a player during an ongoing game session. Please finish or delete the current session first.")
                .setPositiveButton("OK", null)
                .create()
                .show()
            }
            else{
                playerStatsAdapter.deletePlayerStat(position)
            }
        }
        .setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        .create()
        confirmDialog.show()
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