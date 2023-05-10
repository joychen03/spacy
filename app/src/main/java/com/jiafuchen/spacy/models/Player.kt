package com.jiafuchen.spacy.models

import android.content.Context
import android.graphics.BitmapFactory
import com.jiafuchen.spacy.R
import com.jiafuchen.spacy.ui.SpacyGame
import java.util.Random

class Player(val context : Context, val screenWidth : Int, val screenHeight : Int) {

    val player = BitmapFactory.decodeResource(context.resources, R.drawable.space_ship)
    private val random = Random()
    var x = 0
    var y = 0
    var speed = 0

    init {
        resetPosition()
    }

    private fun resetPosition() {
        x = random.nextInt(screenWidth - player.width)
        y = screenHeight - player.height - 200;
        speed = 10 + random.nextInt(6)
    }


}