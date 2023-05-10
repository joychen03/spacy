package com.jiafuchen.spacy.models

import android.content.Context
import android.graphics.BitmapFactory
import com.jiafuchen.spacy.R
import java.util.Random

class Enemy(val context: Context, val screenWidth: Int, val screenHeight: Int) {

    val enemy = BitmapFactory.decodeResource(context.resources, R.drawable.alient_ship)
    val random = Random()
    var x = 0
    var y = 0
    var speed = 0

    init {
        resetPosition()
    }
    private fun resetPosition() {
        x = 200 + random.nextInt(400)
        y = 0
        speed = 14 + random.nextInt(10)
    }


}