package com.mobdeve.machineproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.mobdeve.machineproject.Model.GameSession
import com.mobdeve.machineproject.Model.ViralSkills

class ViralSkillsActivity : AppCompatActivity() {

    private lateinit var onslaught: ImageView
    private lateinit var navigator: ImageView
    private lateinit var tank: ImageView
    private lateinit var acidreflux: ImageView
    private lateinit var agility: ImageView
    private lateinit var apex: ImageView
    private lateinit var sp: TextView
    private var viralSkills: ViralSkills = GameSession.getViral().viralSkills
    private var currentToast: Toast? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.viral_skills)

        onslaught = findViewById(R.id.iv_onslaught)
        navigator = findViewById(R.id.iv_navigator)
        tank = findViewById(R.id.iv_tank)
        acidreflux = findViewById(R.id.iv_acidreflux)
        agility = findViewById(R.id.iv_agility)
        apex = findViewById(R.id.iv_apex)
        sp = findViewById(R.id.tv_skillPoints)

        updateSkillImages()
        updateSkillPoints()

        initializeListeners()
    }

    private fun showSkillInfoDialog(skillName: String, hasSkill: Boolean) {
        val dialog = AlertDialog.Builder(this)
            .setTitle(skillName)
            if(hasSkill){
                dialog.setMessage("Skill already acquired.")
            }
            else{
                dialog.setMessage("Spend 1 skill point to acquire this skill?")
                .setPositiveButton("Yes") { _, _ ->
                    if (viralSkills.skillPoints > 0) {
                        viralSkills.skillPoints--
                        updateSkillPoints()
                        when (skillName) {
                            "Onslaught" -> viralSkills.hasOnslaught = true
                            "Navigator" -> viralSkills.hasNavigator = true
                            "Tank" -> viralSkills.hasTank = true
                            "Acid Reflux" -> viralSkills.hasAcidReflux = true
                            "Agility" -> viralSkills.hasAgility = true
                            "Apex" -> viralSkills.hasApex = true
                        }
                        updateSkillImages()
                    }
                    else {
                        showToast("You don't have any skill points.")
                    }
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
            }
            .create()
        dialog.show()
    }

    private fun initializeListeners(){
        onslaught.setOnClickListener {
            showSkillInfoDialog("Onslaught", viralSkills.hasOnslaught)
        }
        navigator.setOnClickListener {
            if (viralSkills.hasOnslaught) {
                showSkillInfoDialog("Navigator", viralSkills.hasNavigator)
            } else {
                showToast("You need to acquire Onslaught first.")
            }
        }
        tank.setOnClickListener {
            if (viralSkills.hasOnslaught) {
                showSkillInfoDialog("Tank", viralSkills.hasTank)
            } else {
                showToast("You need to acquire Onslaught first.")
            }
        }
        acidreflux.setOnClickListener {
            if (viralSkills.hasNavigator) {
                showSkillInfoDialog("Acid Reflux", viralSkills.hasAcidReflux)
            } else {
                showToast("You need to acquire Navigator first.")
            }
        }
        agility.setOnClickListener {
            if (viralSkills.hasTank) {
                showSkillInfoDialog("Agility", viralSkills.hasAgility)
            } else {
                showToast("You need to acquire Tank first.")
            }
        }
        apex.setOnClickListener {
            if (viralSkills.hasAgility && viralSkills.hasAcidReflux) {
                showSkillInfoDialog("Apex", viralSkills.hasApex)
            } else {
                showToast("You need to acquire both Agility and Acid Reflux first.")
            }
        }

        onslaught.setOnLongClickListener {
            showSkillDescription("Onslaught", "After infecting a survivor, play for another turn. Viral player starts with this skill.")
            true
        }
        navigator.setOnLongClickListener {
            showSkillDescription("Navigator", "Can now use shortcuts. Additionally, can use blue shortcuts from any direction.")
            true
        }
        tank.setOnLongClickListener {
            showSkillDescription("Tank","Negate any weapon or obstacle once per round.")
            true
        }
        acidreflux.setOnLongClickListener {
            showSkillDescription("Acid Reflux", "You may place an acid trap adjacent to your position at the start of every turn. (Only one acid trap can exist at a time, but you may overwrite an old trap by placing a new one.")
            true
        }
        agility.setOnLongClickListener {
            showSkillDescription("Agility","Roll 3 dice at the start of every turn.")
            true
        }
        apex.setOnLongClickListener {
            showSkillDescription("Apex", "Kill survivors on the spot instead of infecting them. Additionally, you may now enter houses.")
            true
        }
    }
    private fun showSkillDescription(skillName: String, description: String) {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder
            .setTitle(skillName)
            .setMessage(description)
            .create()
            .show()
    }

    private fun updateSkillPoints() {
        val msg = "Skill Points: ${viralSkills.skillPoints}"
        sp.text = msg
    }

    private fun updateSkillImages() {
        updateImage(onslaught, viralSkills.hasOnslaught)
        updateImage(navigator, viralSkills.hasNavigator)
        updateImage(tank, viralSkills.hasTank)
        updateImage(acidreflux, viralSkills.hasAcidReflux)
        updateImage(agility, viralSkills.hasAgility)
        updateImage(apex, viralSkills.hasApex)
    }

    private fun updateImage(imageView: ImageView, hasSkill: Boolean) {
        if (hasSkill) {
            imageView.alpha = 1.0f
        } else {
            imageView.alpha = 0.5f
        }
    }

    private fun showToast(message: String) {
        currentToast?.cancel() // Cancel previous toast if it exists
        currentToast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        currentToast?.show()
    }
}