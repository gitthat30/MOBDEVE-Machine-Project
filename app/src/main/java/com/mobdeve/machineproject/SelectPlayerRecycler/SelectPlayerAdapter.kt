package com.mobdeve.machineproject.SelectPlayerRecycler
import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.machineproject.InitializePlayers
import com.mobdeve.machineproject.Model.Player
import com.mobdeve.machineproject.R

class SelectPlayerAdapter(private val clicked: HashMap<String, Any>, private val players: ArrayList<Player>) : RecyclerView.Adapter<PlayerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.select_player_item, parent, false)

        return PlayerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return players.size
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val player = players[position]
        holder.bind(player)

        holder.setOnClickListener {
            //Log playerID and playername with tag Testing Listener

            Log.d("Testing Listener", "onBindViewHolder: ${player.playerID}, ${player.name}")

            val returnIDIntent = Intent()
            returnIDIntent.putExtra(InitializePlayers.PLAYER_ID_KEY, player.playerID)

            if(clicked[InitializePlayers.CLICKED_KEY] == InitializePlayers.VIRAL_CLICKED) {
                returnIDIntent.putExtra(InitializePlayers.CLICKED_KEY, InitializePlayers.VIRAL_CLICKED)
            }
            else if(clicked[InitializePlayers.CLICKED_KEY] == InitializePlayers.PLAYER_CLICKED) {
                returnIDIntent.putExtra(InitializePlayers.CLICKED_KEY, InitializePlayers.PLAYER_CLICKED)
            }

            (holder.itemView.context as? Activity)?.setResult(Activity.RESULT_OK, returnIDIntent)
            (holder.itemView.context as? Activity)?.finish()
        }
    }


    fun addPlayer(player: Player) {
        this.players.add(player)
        notifyItemInserted(players.size - 1)
    }

    fun printKeys() {
        for ((index, player) in players.withIndex()) {
            //Log player names and playerid
            Log.d("Testing PlayerIDS", "printKeys: ${player.name}, ${player.playerID}")
        }
    }
}