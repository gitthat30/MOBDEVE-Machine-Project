package com.mobdeve.machineproject

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.machineproject.InitializePlayerRecycler.InitializePlayerAdapter
import com.mobdeve.machineproject.InitializePlayerRecycler.SwipeCallback
import com.mobdeve.machineproject.Model.GameSession
import com.mobdeve.machineproject.Model.Player
import com.mobdeve.machineproject.SQL.DBHandler
import com.mobdeve.machineproject.SQL.PlayerDatabase

class InitializePlayers : ComponentActivity() {
    companion object {
        val PLAYER_ID_KEY = "PLAYER_ID_KEY"
        val CLICKED_KEY = "CLICKED_KEY"

        val VIRAL_CLICKED = "VIRAL_CLICKED"
        val PLAYER_CLICKED = "PLAYER_CLICKED"
        val PLAYER_NUMBER = "PLAYER_NUMBER"
    }


    //Dummmy Player
    private var viral_Player: Player = Player(-1, "default", 0, 0, 0)


    private lateinit var viral_LL: LinearLayout
    private lateinit var initialize_add_player_btn: Button
    private lateinit var startButton: Button

    private lateinit var playerDatabase: PlayerDatabase
    private lateinit var initializeRecycler: RecyclerView
    private lateinit var initializeRecyclerAdapter: InitializePlayerAdapter
    private lateinit var InitializeToastManager: ToastManager
    private lateinit var itemTouchHelper: ItemTouchHelper

    private val playerList: ArrayList<Player> = ArrayList()

    private val getPlayerIDActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            val playerID = result.data!!.getLongExtra(PLAYER_ID_KEY, -1)
            //Log playerID with tag Testing Result
            Log.d("Testing Result", "onActivityResult: $playerID")

            if(result.data!!.getStringExtra(CLICKED_KEY) == VIRAL_CLICKED) {
                Log.d("Testing Result", "onActivityResult: VIRAL CLICKED")
                val viral_txt: TextView = viral_LL.findViewById(R.id.txtViral)

                val data: Player = playerDatabase.getPlayer(playerID)

                if(playerList.any({it.playerID == data.playerID})) {
                    InitializeToastManager.sendMsg("This player is already in the game")
                }
                else {
                    viral_Player = data
                    viral_Player.isViral = 1
                    viral_txt.text = viral_Player.name
                }
            }
            else if(result.data!!.getStringExtra(CLICKED_KEY) == PLAYER_CLICKED) {
                Log.d("Testing Result", "onActivityResult: PLAYER CLICKED")

                val data: Player = playerDatabase.getPlayer(playerID)

                if(data.playerID == viral_Player.playerID || playerList.any{it.playerID == data.playerID}) {
                    InitializeToastManager.sendMsg("This player is already in the game")
                }
                else {
                    data.isViral = 0
                    playerList.add(data)
                    initializeRecyclerAdapter.notifyDataSetChanged()
                    initializeRecyclerAdapter.printKeys()
                }
            }

            if(playerList.size == 4) {
                initialize_add_player_btn.isClickable = false
            }
        }
        else if (result.resultCode == RESULT_CANCELED) {
            Log.d("Testing Result", "Nothing")
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.initialize_players_layout)

        playerDatabase = PlayerDatabase(applicationContext)

        InitializeToastManager = ToastManager(this)

        initRecycler()
        initViews()
        initListeners()

        val swipeCallback = SwipeCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
        swipeCallback.InitializeAdapter = initializeRecyclerAdapter
        itemTouchHelper = ItemTouchHelper(swipeCallback)
        itemTouchHelper.attachToRecyclerView(initializeRecycler)
    }

    fun initRecycler() {
        //Recycler View
        initializeRecycler = findViewById(R.id.initialize_recycler)
        initializeRecyclerAdapter = InitializePlayerAdapter(playerList)
        initializeRecycler.adapter = initializeRecyclerAdapter
        this.initializeRecycler.layoutManager = LinearLayoutManager(this)
    }

    fun initViews() {
        startButton = findViewById<Button>(R.id.btnStart)
        viral_LL = findViewById<LinearLayout>(R.id.llViral)
        initialize_add_player_btn = findViewById<Button>(R.id.initialize_add_player_btn)

    }

    fun initListeners() {
        startButton.setOnClickListener {
            if(playerList.size < 2) {
                InitializeToastManager.sendMsg("Please add at least 2 players")
            }
            else if (viral_Player.playerID == -1.toLong()) {
                InitializeToastManager.sendMsg("Please add the Viral Player")
            }
            else {
                startButton.isClickable = false
                playerList.add(viral_Player)
                GameSession.reset()
                GameSession.initialize(playerList)
                val intent = Intent(this, MainGame::class.java)
                startActivity(intent)
                finish()
                startButton.postDelayed({
                    startButton.isClickable = true
                }, 1000)
            }
        }

        val selectIntent = Intent(this, SelectPlayer::class.java)

        viral_LL.setOnClickListener {
            selectIntent.putExtra(CLICKED_KEY, VIRAL_CLICKED)
            getPlayerIDActivityLauncher.launch(selectIntent)
        }

        initialize_add_player_btn.setOnClickListener {
            selectIntent.putExtra(CLICKED_KEY, PLAYER_CLICKED)
            getPlayerIDActivityLauncher.launch(selectIntent)
        }
    }
}