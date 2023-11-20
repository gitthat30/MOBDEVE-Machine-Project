package com.mobdeve.machineproject.PlayerStatsRecycler

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.machineproject.Model.Player
import com.mobdeve.machineproject.R
import com.mobdeve.machineproject.SQL.DBHandler
import com.mobdeve.machineproject.SQL.PlayerDatabase

class PlayerStatsAdapter(private val players: ArrayList<Player>, private var activity: Activity, private var displayConfirmPopup: (position: Int) -> Unit) : RecyclerView.Adapter<PlayerStatsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerStatsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.player_stats_item, parent, false)

        return PlayerStatsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return players.size
    }

    override fun onBindViewHolder(holder: PlayerStatsViewHolder, position: Int) {
        val playerStat = players[position]
        holder.bind(playerStat)

        val deleteButton = holder.deleteButton
        deleteButton.setOnClickListener {
            displayConfirmPopup(position)
        }
    }

    fun deletePlayerStat(position: Int) {
        val playerDatabase = PlayerDatabase(activity.applicationContext)
        val toDelete = players[position]
        Log.v("TEST", "DELETING PLAYER: " +toDelete.playerID.toString())
        playerDatabase.deletePlayer(toDelete)

        players.removeAt(position)
        notifyItemRemoved(position)
        notifyDataSetChanged()
    }
}