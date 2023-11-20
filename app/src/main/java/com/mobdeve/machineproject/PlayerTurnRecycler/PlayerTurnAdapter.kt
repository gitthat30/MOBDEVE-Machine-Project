package com.mobdeve.machineproject.PlayerTurnRecycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.machineproject.Model.Player
import com.mobdeve.machineproject.R
import com.mobdeve.machineproject.SelectPlayerRecycler.PlayerTurnViewHolder

class PlayerTurnAdapter(private val players: ArrayList<Player>): RecyclerView.Adapter<PlayerTurnViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerTurnViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.turn_item, parent, false)

        return PlayerTurnViewHolder(view)
    }

    override fun getItemCount(): Int {
        return players.size
    }

    override fun onBindViewHolder(holder: PlayerTurnViewHolder, position: Int) {
        val player = players[position]
        holder.bind(player)
    }
}