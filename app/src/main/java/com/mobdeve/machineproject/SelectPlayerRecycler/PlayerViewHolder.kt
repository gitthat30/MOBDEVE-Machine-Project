package com.mobdeve.machineproject.SelectPlayerRecycler

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mobdeve.machineproject.Model.Player
import com.mobdeve.machineproject.R

class PlayerViewHolder(itemView: View): ViewHolder(itemView) {
    private val playerName: TextView = itemView.findViewById(R.id.player_name)
    private val playerImg: ImageView = itemView.findViewById(R.id.player_img)

    fun bind(player: Player) {
        playerName.text = player.name
        playerImg.setImageResource(player.playerImg)
    }
}