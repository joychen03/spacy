package com.jiafuchen.spacy.models

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import com.jiafuchen.spacy.R
import com.jiafuchen.spacy.ui.constants.GameParams

class Shot(val image : Bitmap, var x : Int, var y : Int) {

    private val screenWidth = 0
    private val screenHeight = 0

    fun draw(canvas: Canvas) {
        canvas.drawBitmap(image, x.toFloat(), y.toFloat(), null)
    }
    fun update(speed : Int) {
        y -= speed
    }
}