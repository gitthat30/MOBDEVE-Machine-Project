package com.mobdeve.machineproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.machineproject.Model.Player
import com.mobdeve.machineproject.SQL.DBHandler
import com.mobdeve.machineproject.SQL.PlayerDatabase
import com.mobdeve.machineproject.SelectPlayerRecycler.SelectPlayerAdapter
import java.util.concurrent.Executors

class SelectPlayer : AppCompatActivity() {
    private lateinit var PlayerDBHandler: DBHandler
    private lateinit var RecyclerAdapter: SelectPlayerAdapter
    private lateinit var SelectToastManager: ToastManager

    private lateinit var recyclerView: RecyclerView
    private lateinit var editPlayerName: EditText
    private lateinit var addPlayerBtn: Button

    private lateinit var playerDatabase: PlayerDatabase
    private var players: ArrayList<Player> = ArrayList()


    private var AvatarID: Int = R.drawable.player1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_add_player_popup)
        SelectToastManager = ToastManager(this)

        val clickedHash = hashMapOf<String, Any>()

        clickedHash[InitializePlayers.CLICKED_KEY] = intent.getStringExtra(InitializePlayers.CLICKED_KEY).toString()

        PlayerDBHandler = DBHandler(this)
        playerDatabase = PlayerDatabase(applicationContext)
        players = playerDatabase.getAllPlayers()

        this.recyclerView = findViewById(R.id.select_recycler)
        RecyclerAdapter = SelectPlayerAdapter(clickedHash, players)
        this.recyclerView.adapter = RecyclerAdapter

        this.recyclerView.layoutManager = LinearLayoutManager(this)

        this.addPlayerBtn = findViewById(R.id.add_btn)
        this.addPlayerBtn.setOnClickListener {
            showAddPlayerDialog()
        }

    }

    fun showAddPlayerDialog() {
        val addPlayerDialog = LayoutInflater.from(this).inflate(R.layout.add_player, null)
        val avatarImg = addPlayerDialog.findViewById<ImageView>(R.id.player_avatar_img)
        val avatarSpinner = addPlayerDialog.findViewById<Spinner>(R.id.player_avatar_spinner)
        editPlayerName = addPlayerDialog.findViewById(R.id.player_name_edit)

        avatarSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selected = parent?.getItemAtPosition(position).toString()

                when(selected) {
                    "Avatar 1" -> AvatarID = R.drawable.player1
                    "Avatar 2" -> AvatarID = R.drawable.player2
                    "Avatar 3" -> AvatarID = R.drawable.player3
                    "Avatar 4" -> AvatarID = R.drawable.player4
                }

                avatarImg.setImageResource(AvatarID)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                ;
            }
        }

        val builder = AlertDialog.Builder(this)
        builder.setView(addPlayerDialog)
            .setTitle("Add Player")
            .setPositiveButton("Add") { dialog, _ ->
                val playerToAdd = Player(-1, editPlayerName.getText().toString(), AvatarID, 0, 0)

                if(players.any{it.name == playerToAdd.name}) {
                    SelectToastManager.sendMsg("Player name already taken")
                }
                else if(playerToAdd.name.length > 10) {
                    SelectToastManager.sendMsg("Please limit player name to 10 characters")
                }
                else {
                    val key = playerDatabase.insertPlayer(playerToAdd)
                    playerToAdd.playerID = key
                    RecyclerAdapter.addPlayer(playerToAdd)
                    RecyclerAdapter.printKeys()
                }

            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }

        val alertDialog = builder.create()
        alertDialog.show()
    }


}
