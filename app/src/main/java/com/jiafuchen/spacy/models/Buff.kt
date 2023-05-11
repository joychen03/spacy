package com.jiafuchen.spacy.models

import android.graphics.Bitmap
import android.graphics.Canvas

abstract class Buff {
    abstract val image : Bitmap
    var x: Int = 0
    var y: Int = 0
    abstract fun draw(canva : Canvas)
    abstract fun update()
    abstract fun applyBuff(player: Player)
}