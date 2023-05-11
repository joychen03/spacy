package com.jiafuchen.spacy.models

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import com.jiafuchen.spacy.ui.constants.GameParams

class Player(val image : Bitmap) {

    var x: Int = 0
    var y: Int = 0
    var shotSpeed = GameParams.PLAYER_BULLET_SPEED
    var shotFrequency = GameParams.PLAYER_BULLET_FREQUENCY
    var shotDamage = GameParams.PLAYER_BULLET_DAMAGE

    private val screenWidth = Resources.getSystem().displayMetrics.widthPixels
    private val screenHeight = Resources.getSystem().displayMetrics.heightPixels

    init {
        x = screenWidth/2
        y = screenHeight/2
    }

    fun draw(canvas: Canvas) {
        canvas.drawBitmap(image, x.toFloat(), y.toFloat(), null)
    }

    fun update(touchX: Int, touchY: Int) {
        x = touchX - image.width / 2
        y = touchY - image.height / 2
    }


}