package com.jiafuchen.spacy.ui.controllers

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.jiafuchen.spacy.R
import com.jiafuchen.spacy.models.Enemy


class SpacyGameView(context : Context, attributes: AttributeSet) : SurfaceView(context, attributes), SurfaceHolder.Callback{

    private val thread: GameThread
    private var enemy: Enemy? = null

    private val enemyImages = listOf(
        R.drawable.alient_1,
        R.drawable.alient_2,
        R.drawable.alient_3,
        R.drawable.alient_4,
        R.drawable.alient_5,
        R.drawable.alient_6,
        R.drawable.alient_7,
        R.drawable.alient_ship
    )

    init {
        holder.addCallback(this)
        thread = GameThread(holder,this)
    }

    override fun surfaceCreated(surfaceHolder: SurfaceHolder) {
        // start the game thread

        enemy = Enemy(getResizedBitmap(R.drawable.alient_1, 300, 300))

        thread.setRunning(true)
        thread.start()
    }

    override fun surfaceChanged(surfaceHolder: SurfaceHolder, i: Int, i1: Int, i2: Int) {

    }

    override fun surfaceDestroyed(surfaceHolder: SurfaceHolder) {
        var retry = true
        while (retry) {
            try {
                thread.setRunning(false)
                thread.join()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            retry = false
        }
    }

    /**
     * Function to update the positions of player and game objects
     */
    fun update() {
        enemy!!.update()
    }

    /**
     * Everything that has to be drawn on Canvas
     */
    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        enemy!!.draw(canvas)
    }

    private fun getResizedBitmap(resourceId: Int, width: Int, height: Int): Bitmap {
        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
            BitmapFactory.decodeResource(resources, resourceId)
            inSampleSize = calculateInSampleSize(this, width, height)
            inJustDecodeBounds = false
        }
        val bitmap = BitmapFactory.decodeResource(resources, resourceId, options)
        return Bitmap.createScaledBitmap(bitmap, width, height, true)
    }

    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val halfHeight = height / 2
            val halfWidth = width / 2

            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }

        return inSampleSize
    }

    private fun getRandomEnemy() : Int{
        val size = enemyImages.size - 1
        return enemyImages[(0..size).random()]
    }


}