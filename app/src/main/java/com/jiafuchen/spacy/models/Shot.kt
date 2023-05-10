package com.jiafuchen.spacy.models

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.jiafuchen.spacy.R

class Shot(val context : Context, val screenWidth : Int, val screenHeight : Int) {

    val bullet = BitmapFactory.decodeResource(context.resources, R.drawable.bullet)

    fun getShot() : Bitmap {
        return bullet
    }

}