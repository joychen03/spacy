package com.jiafuchen.spacy.models

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import com.jiafuchen.spacy.ui.constants.GameParams

class FrequencyBuff(override val image: Bitmap) : Buff() {

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
        if(player.shotFrequency - GameParams.BUFF_FREQUENCY_INCREASE < GameParams.PLAYER_MAX_BULLET_FREQUENCY){
            player.shotFrequency = GameParams.PLAYER_MAX_BULLET_FREQUENCY
            return
        }else{
            player.shotFrequency -= GameParams.BUFF_FREQUENCY_INCREASE
        }
    }

}