package com.mobdeve.machineproject.InitializePlayerRecycler

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.machineproject.Model.Player
import com.mobdeve.machineproject.R
import com.mobdeve.machineproject.SelectPlayerRecycler.PlayerViewHolder

class InitializePlayerAdapter(private val players: ArrayList<Player>, private val avatarIDs: ArrayList<Int>) : RecyclerView.Adapter<InitializeViewHolder>() {
    private val allAvatarIDs = arrayOf(
        R.drawable.survivor1,
        R.drawable.survivor2,
        R.drawable.survivor3,
        R.drawable.survivor4,
        R.drawable.survivor5,
        R.drawable.survivor6,
        R.drawable.survivor7,
        R.drawable.survivor8
    )

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

        if(!avatarIDs.contains(holder.getAvatar())) {
            avatarIDs.add(holder.getAvatar())
        }

        Log.d("start", "${avatarIDs}")
        holder.setOnClickListener {
            Log.d("TAG", "${avatarIDs}")
            var finished = false
            var start = allAvatarIDs.indexOf(holder.getAvatar())
            allAvatarIDs.drop(start).forEach {
                if(!finished) {
                    if(!avatarIDs.contains(it)) {
                        Log.d("TAG", "$avatarIDs $finished")
                        avatarIDs.remove(holder.getAvatar())
                        avatarIDs.add(it)
                        holder.setImage(it)
                        finished = true
                    }
                }
            }
            if(!finished) {
                allAvatarIDs.forEach {
                    if(!finished) {
                        if(!avatarIDs.contains(it)) {
                            Log.d("TAG", "$avatarIDs $finished")
                            avatarIDs.remove(holder.getAvatar())
                            avatarIDs.add(it)
                            holder.setImage(it)
                            finished = true
                        }
                    }
                }
            }
        }
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