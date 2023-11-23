package com.mobdeve.machineproject.PlayerStatsRecycler

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mobdeve.machineproject.Model.Player
import com.mobdeve.machineproject.R

class PlayerStatsViewHolder(itemView: View): ViewHolder(itemView) {
    private val playerStatName: TextView = itemView.findViewById(R.id.playerStat_name)
    private val playerStatImg: ImageView = itemView.findViewById(R.id.playerStat_img)
    private val survivorGames: TextView = itemView.findViewById(R.id.survivorGames_numbers)
    private val survivorWins: TextView = itemView.findViewById(R.id.survivorWins_numbers)
    private val viralGames: TextView = itemView.findViewById(R.id.viralGames_numbers)
    private val viralInfections: TextView = itemView.findViewById(R.id.viralInfection_numbers)
    val deleteButton: ImageButton = itemView.findViewById(R.id.playerStatDelete)

    fun bind(playerStat: Player) {
        playerStatName.text = playerStat.name
        playerStatImg.setImageResource(playerStat.playerImg)
        survivorGames.text = playerStat.numSurvivorPlayed.toString()
        survivorWins.text = playerStat.numSurvivorWins.toString()
        viralGames.text = playerStat.numViralPlayed.toString()
        viralInfections.text = playerStat.numViralInfections.toString()
    }
}