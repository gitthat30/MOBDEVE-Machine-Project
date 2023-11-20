package com.mobdeve.machineproject.SelectPlayerRecycler

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mobdeve.machineproject.Model.Player
import com.mobdeve.machineproject.R

class PlayerTurnViewHolder(itemView: View): ViewHolder(itemView) {
    private val playerName: TextView = itemView.findViewById(R.id.turn_name)
    private val playerImg: ImageView = itemView.findViewById(R.id.turn_img)

    fun bind(player: Player) {
        val maxLength = 10
        val ellipsis = "..."

        if (player.name.length <= maxLength) {
            playerName.text = player.name
        } else {
            val truncatedName = player.name.substring(0, maxLength - ellipsis.length) + ellipsis
            playerName.text = truncatedName
        }

        playerImg.setImageResource(player.playerImg)
    }
}