package com.mobdeve.machineproject

import android.content.Context
import android.widget.Toast

//For displaying notifications at the bottom of the screen
class ToastManager(private val context: Context) {

    fun sendMsg(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
}