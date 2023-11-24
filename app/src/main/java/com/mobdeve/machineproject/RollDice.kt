package com.mobdeve.machineproject

import android.app.AlertDialog
import android.graphics.drawable.AnimationDrawable
import android.media.Image
import android.media.MediaPlayer
import android.opengl.Visibility
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.core.view.GestureDetectorCompat
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.ComponentActivity
import com.mobdeve.machineproject.Model.GameSession

class RollDice : ComponentActivity(), GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener  {
    private lateinit var column1: LinearLayout
    private lateinit var column2: LinearLayout

    private lateinit var dice1: ImageView
    private lateinit var dice2: ImageView
    private lateinit var dice3: ImageView
    private lateinit var dice4: ImageView

    private lateinit var rain: ImageView
    private lateinit var cramp: ImageView

    private lateinit var backButton: Button
    private lateinit var minusButton: ImageButton
    private lateinit var plusButton: ImageButton

    private var diceNum: Int = 2
    private var diceValues: Array<Int> = arrayOf(0, 0, 0, 0)
    private var diceImages: Array<Int> = arrayOf(
        R.drawable.xml_dice1,
        R.drawable.xml_dice2,
        R.drawable.xml_dice3,
        R.drawable.xml_dice4,
        R.drawable.xml_dice5,
        R.drawable.xml_dice6)
    private var diceTapped: Int = -1

    private lateinit var gestureDetect: GestureDetectorCompat
    private val handler = Handler()
    private var diceSFX1: MediaPlayer? = null
    private var diceSFX2: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dice_roll)

        initViews()
        initListeners()
        initDiceValues()
        initDoubleTapListeners()
        initMediaPlayers()

        updateClickable()
        updateDiceImages()
        updateDiceCount()
    }

    fun initMediaPlayers() {
        diceSFX1 = MediaPlayer.create(this, R.raw.dicesfx1)
        diceSFX2 = MediaPlayer.create(this, R.raw.dicesfx2)
    }

    fun initViews() {
        column1 = findViewById(R.id.dice_column1)
        column2 = findViewById(R.id.dice_column2)

        dice1 = column1.findViewById(R.id.dice1_img)
        dice2 = column1.findViewById(R.id.dice2_img)
        dice3 = column2.findViewById(R.id.dice3_img)
        dice4 = column2.findViewById(R.id.dice4_img)

        rain = findViewById(R.id.iv_rain)
        cramp = findViewById(R.id.iv_cramp)
        if(GameSession.rainValue > 0){
            rain.visibility = View.VISIBLE
        }
        else{
            rain.visibility = View.GONE
        }
        if(GameSession.getCurrentPlayer().muscleCramps){
            cramp.visibility = View.VISIBLE
        }
        else{
            cramp.visibility = View.GONE
        }

        minusButton = findViewById(R.id.minus_btn)
        plusButton = findViewById(R.id.plus_btn)
    }

    fun initListeners() {
        minusButton.setOnClickListener {
            diceNum--
            updateClickable()
            updateDiceCount()
        }

        plusButton.setOnClickListener {
            diceNum++
            updateClickable()
            updateDiceCount()
        }

        rain.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Rain Event")
            val turns = if (GameSession.rainValue <= 1) "turn" else "turns"
            builder.setMessage("All dice roll results are halved. ${GameSession.rainValue} $turns remaining.")
            builder.create().show()
        }
        cramp.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Muscle Cramp Event")
            builder.setMessage("You get one less dice roll this turn.")
            builder.create().show()
        }
    }

    fun initDoubleTapListeners() {
        gestureDetect = GestureDetectorCompat(this, this)

        dice1.setOnTouchListener() {_, event ->
            diceTapped = 1
            gestureDetect.onTouchEvent(event)
        }

        dice2.setOnTouchListener() {_, event ->
            diceTapped = 2
            gestureDetect.onTouchEvent(event)
        }

        dice3.setOnTouchListener() {_, event ->
            diceTapped = 3
            gestureDetect.onTouchEvent(event)
        }

        dice4.setOnTouchListener() {_, event ->
            diceTapped = 4
            gestureDetect.onTouchEvent(event)
        }
    }

    fun updateClickable() {
        minusButton.isClickable = true
        plusButton.isClickable = true

        if(diceNum == 1) {
            minusButton.isClickable = false
        }
        else if (diceNum == 4) {
            plusButton.isClickable = false
        }
    }

    fun updateDiceImages() {
        dice1.setImageResource(diceImages[diceValues[0]-1])
        dice2.setImageResource(diceImages[diceValues[1]-1])
        dice3.setImageResource(diceImages[diceValues[2]-1])
        dice4.setImageResource(diceImages[diceValues[3]-1])
    }
    fun updateDiceCount() {
        dice1.visibility = View.GONE
        dice2.visibility = View.GONE
        dice3.visibility = View.GONE
        dice4.visibility = View.GONE

        when(diceNum) {
            1 -> {
                dice1.visibility = View.VISIBLE
            }
            2 -> {
                dice1.visibility = View.VISIBLE
                dice2.visibility = View.VISIBLE
            }
            3 -> {
                dice1.visibility = View.VISIBLE
                dice2.visibility = View.VISIBLE
                dice3.visibility = View.VISIBLE
            }
            4 -> {
                dice1.visibility = View.VISIBLE
                dice2.visibility = View.VISIBLE
                dice3.visibility = View.VISIBLE
                dice4.visibility = View.VISIBLE
            }
        }
    }

    override fun onDoubleTap(e: MotionEvent): Boolean {
        var selectedDice: ImageView = dice1
        var selectedDiceAnim: AnimationDrawable = createDiceAnimation()

        when(diceTapped) {
            1 -> {
                selectedDice = dice1
                Log.d("Testing Double Tap", "Dice Value: ${diceValues[diceTapped-1]}")
            }
            2 -> {
                selectedDice = dice2
                Log.d("Testing Double Tap", "Dice Value: ${diceValues[diceTapped-1]}")
            }
            3 -> {
                selectedDice = dice3
                Log.d("Testing Double Tap", "Dice Value: ${diceValues[diceTapped-1]}")
            }
            4 -> {
                selectedDice = dice4
                Log.d("Testing Double Tap", "Dice Value: ${diceValues[diceTapped-1]}")
            }
        }

        selectedDice.setImageDrawable(selectedDiceAnim)

        selectedDiceAnim = selectedDice.drawable as AnimationDrawable

        diceSFX1!!.start()
        selectedDiceAnim.start()


        selectedDice.postDelayed({
            selectedDiceAnim.stop()
            selectedDice.setImageResource(diceImages[(0..5).random()])
        }, (450..600).random().toLong())

        return true
    }

    override fun onLongPress(e: MotionEvent) {
        var diceAnim1: AnimationDrawable = createDiceAnimation()
        var diceAnim2: AnimationDrawable = createDiceAnimation()
        var diceAnim3: AnimationDrawable = createDiceAnimation()
        var diceAnim4: AnimationDrawable = createDiceAnimation()

        dice1.setImageDrawable(diceAnim1)
        dice2.setImageDrawable(diceAnim2)
        dice3.setImageDrawable(diceAnim3)
        dice4.setImageDrawable(diceAnim4)

        diceSFX2?.start()

        diceAnim1.start()
        diceAnim2.start()
        diceAnim3.start()
        diceAnim4.start()

        handler.postDelayed({
            diceAnim1.stop()
            diceAnim2.stop()
            diceAnim3.stop()
            diceAnim4.stop()

            dice1.setImageResource(diceImages[(0..5).random()])
            dice2.setImageResource(diceImages[(0..5).random()])
            dice3.setImageResource(diceImages[(0..5).random()])
            dice4.setImageResource(diceImages[(0..5).random()])
        }, (450..600).random().toLong())
    }

    private fun createDiceAnimation(): AnimationDrawable {
        val diceShuffle = AnimationDrawable()
        val randomDrawables = arrayOf(
            diceImages[(0..5).random()],
            diceImages[(0..5).random()],
            diceImages[(0..5).random()],
            diceImages[(0..5).random()],
            diceImages[(0..5).random()],
            diceImages[(0..5).random()]
        )

        randomDrawables.forEach {
            diceShuffle.addFrame(this.resources.getDrawable(it, null), (75..100).random())
        }

        return diceShuffle
    }

    private fun getTotalAnimationDuration(animationDrawable: AnimationDrawable): Long {
        var duration: Long = 0
        for (i in 0 until animationDrawable.numberOfFrames) {
            duration += animationDrawable.getDuration(i)
        }
        return duration
    }

    override fun onDoubleTapEvent(e: MotionEvent): Boolean {
        return true
    }

    fun initDiceValues() {
        diceValues = arrayOf(
            (1..6).random(),
            (1..6).random(),
            (1..6).random(),
            (1..6).random()
        )
    }

    override fun onDown(e: MotionEvent): Boolean {
        return true
    }

    override fun onShowPress(e: MotionEvent) {
        ;
    }

    override fun onSingleTapUp(e: MotionEvent): Boolean {
        return true
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        return true
    }



    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        return true
    }

    override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
        return true
    }
}
