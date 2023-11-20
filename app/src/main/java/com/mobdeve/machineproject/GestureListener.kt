package com.mobdeve.machineproject

import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent

class GestureListener: GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {
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

    override fun onLongPress(e: MotionEvent) {
        ;
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

    override fun onDoubleTap(e: MotionEvent): Boolean {
        Log.d("Test Double Tap", "It works!")

        return true
    }

    override fun onDoubleTapEvent(e: MotionEvent): Boolean {
        return true
    }
}