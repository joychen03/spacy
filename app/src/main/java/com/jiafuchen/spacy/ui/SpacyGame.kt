package com.jiafuchen.spacy.ui

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Point
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.SurfaceView
import android.view.View
import com.jiafuchen.spacy.R
import com.jiafuchen.spacy.models.Enemy
import com.jiafuchen.spacy.models.Hit
import com.jiafuchen.spacy.models.Player
import java.util.Random

class SpacyGame(context : Context, private val size : Point) : SurfaceView(context){

    val FPS : Long = 30;
    val lifeImage = BitmapFactory.decodeResource(context.resources, R.drawable.heart)

    val enemy = Enemy(context, this.width, this.height)
    val player = Player(context, this.width, this.height)
    val hitmarker = Hit(context, this.width, this.height)
    val random = Random()
    val shots : MutableList<Hit> = mutableListOf()


    var enemyHit = false


    init {

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        println("asdasd")
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val x = event?.x

        return super.onTouchEvent(event)
    }

}