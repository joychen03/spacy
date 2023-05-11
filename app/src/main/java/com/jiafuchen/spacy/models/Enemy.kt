package com.jiafuchen.spacy.models

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import com.jiafuchen.spacy.ui.constants.GameParams

class Enemy(val image : Bitmap) {

    var x: Int = 0
    var y: Int = 0
    var life = GameParams.ENEMY_LIFE
    var xVelocity = (GameParams.ENEMY_MIN_SPEED ..GameParams.ENEMY_MAX_SPEED).random()
    var yVelocity = (GameParams.ENEMY_MIN_SPEED ..GameParams.ENEMY_MAX_SPEED).random()

    private val screenWidth = Resources.getSystem().displayMetrics.widthPixels
    private val screenHeight = Resources.getSystem().displayMetrics.heightPixels
    init {
        when ((1..4).random()) {
            1 -> {
                x = 0
                y = 0
            }
            2 -> {
                x = screenWidth - image.width
                y = 0
            }
            3 -> {
                x = 0
                y = screenHeight - image.height
            }
            4 -> {
                x = screenWidth - image.width
                y = screenHeight - image.height
            }
        }
    }

    /**
     * Draws the object on to the canvas.
     */
    fun draw(canvas: Canvas) {
        canvas.drawBitmap(image, x.toFloat(), y.toFloat(), null)
    }

    /**
     * update properties for the game object
     */
    fun update() {
        // val randomNum = ThreadLocalRandom.current().nextInt(1, 5)
        if (x > screenWidth - image.width || x < 0) {
            xVelocity *= -1
        }
        if (y > screenHeight - image.height || y < 0) {
            yVelocity *= -1
        }

        x += (xVelocity)
        y += (yVelocity)
    }

    fun hit( damage : Int) {
        life -= damage
    }

    fun isDead() : Boolean {
        return life <= 0
    }

}