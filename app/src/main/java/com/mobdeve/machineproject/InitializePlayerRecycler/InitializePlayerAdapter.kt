package com.mobdeve.machineproject.InitializePlayerRecycler

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.machineproject.Model.Player
import com.mobdeve.machineproject.R
import com.mobdeve.machineproject.SelectPlayerRecycler.PlayerViewHolder

class InitializePlayerAdapter(private val players: ArrayList<Player>) : RecyclerView.Adapter<InitializeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InitializeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.initialize_players_item, parent, false)

        return InitializeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return players.size
    }

    override fun onBindViewHolder(holder: InitializeViewHolder, position: Int) {
        val player = players[position]
        holder.bind(player, position + 1)
    }

    fun removePlayer(position: Int) {
        this.players.removeAt(position)
        notifyDataSetChanged()
    }

    fun printKeys() {
        for ((index, player) in players.withIndex()) {
            //Log player names and playerid
            Log.d("Testing PlayerIDS", "printKeys: ${player.name}, ${player.playerID}")
        }
    }
}