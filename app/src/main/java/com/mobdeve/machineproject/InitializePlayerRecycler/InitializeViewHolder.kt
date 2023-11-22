package com.mobdeve.machineproject.InitializePlayerRecycler

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.machineproject.Model.Player
import com.mobdeve.machineproject.R

class InitializeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val playername: TextView = itemView.findViewById(R.id.initialize_survivor_name)
    private val player_avatar: ImageView = itemView.findViewById(R.id.initialize_survivor_img)

    fun bind(player: Player, avatar: Int) {
        this.playername.text = player.name

        //Set image resource to survivorN based on avatar
        when(avatar) {
            1 -> {
                player_avatar.setImageResource(R.drawable.survivor1)
                player_avatar.tag = R.drawable.survivor1
            }
            2 -> {
                player_avatar.setImageResource(R.drawable.survivor2)
                player_avatar.tag = R.drawable.survivor2
            }
            3 -> {
                player_avatar.setImageResource(R.drawable.survivor3)
                player_avatar.tag = R.drawable.survivor3
            }
            4 -> {
                player_avatar.setImageResource(R.drawable.survivor4)
                player_avatar.tag = R.drawable.survivor4
            }
            5 -> {
                player_avatar.setImageResource(R.drawable.survivor5)
                player_avatar.tag = R.drawable.survivor5
            }
            6 -> {
                player_avatar.setImageResource(R.drawable.survivor6)
                player_avatar.tag = R.drawable.survivor6
            }
            7 -> {
                player_avatar.setImageResource(R.drawable.survivor7)
                player_avatar.tag = R.drawable.survivor7
            }
            8 -> {
                player_avatar.setImageResource(R.drawable.survivor8)
                player_avatar.tag = R.drawable.survivor8
            }
        }
    }

    fun getAvatar(): Int {
        return player_avatar.tag as Int
    }
    fun setOnClickListener(listener: View.OnClickListener) {
        player_avatar.setOnClickListener(listener)
    }

    fun setImage(image: Int) {
        player_avatar.setImageResource(image)
        player_avatar.tag = image
    }
}