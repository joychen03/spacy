package com.jiafuchen.spacy.models

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.jiafuchen.spacy.R

class Hit(val context : Context, val screenWidth : Int, val screenHeight : Int) {
    val hitmarker = BitmapFactory.decodeResource(context.resources, R.drawable.hitmarker)

    fun getHitMarker() : Bitmap {
        return hitmarker
    }
}