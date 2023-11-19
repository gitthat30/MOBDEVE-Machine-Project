package com.mobdeve.machineproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.machineproject.Model.Player
import com.mobdeve.machineproject.SQL.DBHandler
import com.mobdeve.machineproject.SQL.PlayerDatabase
import com.mobdeve.machineproject.SelectPlayerRecycler.SelectPlayerAdapter
import java.util.concurrent.Executors

class SelectPlayer : AppCompatActivity() {
    private val executorService = Executors.newSingleThreadExecutor()
    private lateinit var PlayerDBHandler: DBHandler
    private lateinit var RecyclerAdapter: SelectPlayerAdapter

    private lateinit var recyclerView: RecyclerView
    private lateinit var editPlayerName: EditText
    private lateinit var addPlayerBtn: Button

    private lateinit var playerDatabase: PlayerDatabase


    private var AvatarID: Int = R.drawable.player1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_add_player_popup)

        PlayerDBHandler = DBHandler(this)
        Log.d("test", "onCreate: Created DBHandler in SelectPlayer")
        playerDatabase = PlayerDatabase(applicationContext)
        val players = playerDatabase.getAllPlayers()

        this.recyclerView = findViewById(R.id.select_recycler)
        RecyclerAdapter = SelectPlayerAdapter(players)
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
                val playerToAdd = Player(editPlayerName.getText().toString(), AvatarID, 0, 0, 0, 0)
                playerDatabase.insertPlayer(playerToAdd)
                RecyclerAdapter.addPlayer(playerToAdd)
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }

        val alertDialog = builder.create()
        alertDialog.show()
    }


}
