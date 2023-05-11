package com.jiafuchen.spacy.models

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import com.jiafuchen.spacy.R

class Hit(val image : Bitmap, var x : Int, var y : Int) {
    fun draw(canvas: Canvas) {
        canvas.drawBitmap(image, x.toFloat(), y.toFloat(), null)
    }

}