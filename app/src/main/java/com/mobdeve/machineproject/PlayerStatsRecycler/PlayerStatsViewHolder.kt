package com.mobdeve.machineproject.PlayerStatsRecycler

import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mobdeve.machineproject.Model.Player
import com.mobdeve.machineproject.R

class PlayerStatsViewHolder(itemView: View): ViewHolder(itemView) {
    private val playerStatName: TextView = itemView.findViewById(R.id.playerStat_name)
    private val playerStatImg: ImageView = itemView.findViewById(R.id.playerStat_img)
    private val playerStatTimesPlayed: TextView = itemView.findViewById(R.id.playerStat_timesPlayed)
    private val playerStatTimesWon: TextView = itemView.findViewById(R.id.playerStat_timesWon)
    val deleteButton: ImageButton = itemView.findViewById(R.id.playerStatDelete)

    fun bind(playerStat: Player) {
        playerStatName.text = playerStat.name
        playerStatImg.setImageResource(playerStat.playerImg)
//        playerStatTimesPlayed.text = playerStat.timesPlayed.toString()
//        playerStatTimesWon.text = playerStat.timesWon.toString()

        // temporary -- might change depending on what stats we want to show
        playerStatTimesPlayed.text = playerStat.numSurvivor.toString()
        playerStatTimesWon.text = playerStat.numSurvivorWins.toString()
    }
}