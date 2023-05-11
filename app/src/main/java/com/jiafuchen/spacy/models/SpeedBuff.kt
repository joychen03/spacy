package com.jiafuchen.spacy.models

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import com.jiafuchen.spacy.ui.constants.GameParams

class SpeedBuff(override val image: Bitmap) : Buff() {

    private val screenWidth = Resources.getSystem().displayMetrics.widthPixels
    private val screenHeight = Resources.getSystem().displayMetrics.heightPixels

    init {
        x = (image.width..screenWidth - image.width).random()
        y = (image.height..screenHeight - image.height).random()
    }
    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(image, x.toFloat(), y.toFloat(), null)
    }

    override fun update() {
        //Buff not moving
    }

    override fun applyBuff(player: Player) {
        if(player.shotSpeed + GameParams.BUFF_SPEED_INCREASE > GameParams.PLAYER_MAX_BULLET_SPEED) return
        player.shotSpeed += GameParams.BUFF_SPEED_INCREASE
    }
}