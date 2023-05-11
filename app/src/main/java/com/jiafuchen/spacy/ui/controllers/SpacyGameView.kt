package com.jiafuchen.spacy.ui.controllers

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.jiafuchen.spacy.R
import com.jiafuchen.spacy.models.Buff
import com.jiafuchen.spacy.models.DamageBuff
import com.jiafuchen.spacy.models.Enemy
import com.jiafuchen.spacy.models.FrequencyBuff
import com.jiafuchen.spacy.models.Hit
import com.jiafuchen.spacy.models.Player
import com.jiafuchen.spacy.models.Shot
import com.jiafuchen.spacy.models.SpeedBuff
import com.jiafuchen.spacy.ui.ResultActivity
import com.jiafuchen.spacy.ui.constants.GameParams
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class SpacyGameView(context : Context, attributes: AttributeSet) : SurfaceView(context, attributes), SurfaceHolder.Callback {
    companion object{
        var BACKGROUND_IMAGE = R.drawable.background
        var PLAYER_IMAGE = R.drawable.space_ship
        var PLAYER_SIZE = 200
        var ENEMY_SIZE = 200
        var MAX_ENEMIES = 10
        var MAX_SPEED = 20
        var MIN_SPEED = 5

        var ENEMIES_IMAGES = mutableListOf(
            R.drawable.alient_1,
            R.drawable.alient_2,
            R.drawable.alient_3,
            R.drawable.alient_4,
            R.drawable.alient_5,
            R.drawable.alient_6,
            R.drawable.alient_7,
        )
    }

    var firstInit = true
    var paused = false
    val thread: GameThread
    var generatingEnemyJob : Job? = null
    var shottingJob : Job? = null
    var playerBuffJob : Job? = null

    private var enemies = mutableListOf<Enemy>()
    private var shots = mutableListOf<Shot>()
    private var buffs = mutableListOf<Buff>()
    private val player = Player(getResizedBitmap(PLAYER_IMAGE, PLAYER_SIZE, PLAYER_SIZE))
    private val background = getResizedBitmap(BACKGROUND_IMAGE, Resources.getSystem().displayMetrics.widthPixels, Resources.getSystem().displayMetrics.heightPixels)
    private var touched: Boolean = false
    private var touched_x: Int = 0
    private var touched_y: Int = 0

    init {
        holder.addCallback(this)
        thread = GameThread(holder,this)
    }

    override fun surfaceCreated(surfaceHolder: SurfaceHolder) {
        // start the game thread

        thread.setRunning(true)
        thread.start()

        startJobs()
//        generatingEnemyJob = CoroutineScope(Dispatchers.IO).launch {
//            while (enemies.size < MAX_ENEMIES){
//                val enemy = Enemy(getResizedBitmap(ENEMIES_IMAGES.random(), ENEMY_SIZE, ENEMY_SIZE))
//                enemies.add(enemy)
//                delay((2000..10000).random().toLong())
////                if(player.shotFrequency - 200 < 0) player.shotFrequency = 50 else player.shotFrequency -= 200
////                player.shotDamage ++
////                player.shotSpeed += 5
//            }
//        }


    }

    override fun surfaceChanged(surfaceHolder: SurfaceHolder, i: Int, i1: Int, i2: Int) {

    }

    override fun surfaceDestroyed(surfaceHolder: SurfaceHolder) {

    }

    fun update() {
        if(!paused){
            enemies.forEach { it.update() }
            if(touched) {
                player.update(touched_x, touched_y)
            }
            shots.forEach { it.update(player.shotSpeed) }
        }
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        if(!paused){
            canvas.drawBitmap(background, 0f, 0f, null)
            for(i in enemies.lastIndex downTo 0){
                if(!checkEnemy(enemies[i])){
                    enemies.removeAt(i)
                }else{
                    enemies[i].draw(canvas)
                }
            }
            player.draw(canvas)
            for(i in shots.lastIndex downTo 0){
                if(!checkShot(shots[i])){
                    val hitMarker = Hit(getResizedBitmap(R.drawable.hitmarker, 100, 100), shots[i].x, shots[i].y - shots[i].image.height * 2)
                    hitMarker.draw(canvas)
                    shots.removeAt(i)
                }else{
                    shots[i].draw(canvas)
                }
            }
            for(i in buffs.lastIndex downTo 0){
                if(!checkBuff(buffs[i])){
                    buffs.removeAt(i)
                }else{
                    buffs[i].draw(canvas)
                }
            }
        }

    }

    private fun checkBuff(buff: Buff): Boolean {
        if(
            (buff.x < player.x + player.image.width) &&
            (buff.x + buff.image.width > player.x) &&
            (buff.y < player.y + player.image.height) &&
            (buff.y + buff.image.height > player.y)
        ){
            buff.applyBuff(player)
            return false
        }
        return true
    }

    private fun checkShot(shot: Shot) : Boolean{
        if(shot.y < 0) return false
        enemies.forEach {
            if(
                (it.x < shot.x + shot.image.width) &&
                (it.x + it.image.width > shot.x) &&
                (it.y < shot.y + shot.image.height) &&
                (it.y + it.image.height > shot.y)
            ){
                it.hit(player.shotDamage)
                return false
            }
        }
        return true
    }

    private fun checkEnemy(enemy: Enemy) : Boolean {
        val playerWidth = player.image.width / 2
        val playerHeight = player.image.height / 2
        val enemyWidth = enemy.image.width / 2
        val enemyHeight = enemy.image.height / 2

        if(
            (enemy.x < player.x + playerWidth) &&
            (enemy.x + enemyWidth > player.x) &&
            (enemy.y < player.y + playerHeight) &&
            (enemy.y + enemyHeight > player.y)
        ){
            CoroutineScope(Dispatchers.Main).launch {
                finish()
                val intent = Intent(context, ResultActivity::class.java)
                context.startActivity(intent)
            }
        }

        if(enemy.isDead()){
            return false
        }

        return true
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        touched_x = event.x.toInt()
        touched_y = event.y.toInt()

        when (event.action) {
            MotionEvent.ACTION_DOWN -> touched = true
            MotionEvent.ACTION_MOVE -> touched = true
            MotionEvent.ACTION_UP -> touched = false
            MotionEvent.ACTION_CANCEL -> touched = false
            MotionEvent.ACTION_OUTSIDE -> touched = false
        }

        return true
    }

    fun startJobs(){
        paused = false
        generatingEnemyJob = CoroutineScope(Dispatchers.IO).launch {
            while (enemies.size < MAX_ENEMIES){
                if(firstInit){
                    delay(1000)
                    firstInit = false
                }else{
                    delay((5000..10000).random().toLong())
                }
                val enemy = Enemy(getResizedBitmap(ENEMIES_IMAGES.random(), ENEMY_SIZE, ENEMY_SIZE))
                enemies.add(enemy)
            }
        }
        shottingJob = CoroutineScope(Dispatchers.IO).launch {
            while (true){
                delay(player.shotFrequency.toLong())
                val shot = Shot(getResizedBitmap(R.drawable.bullet,50,50), (player.x + player.image.width/2 - 25), player.y)
                shots.add(shot)
            }
        }

        playerBuffJob = CoroutineScope(Dispatchers.IO).launch {
            while (true){
                delay((5000..10000).random().toLong())
                (0..2).random().let {
                    when(it){
                        0 -> {
                            buffs.add(DamageBuff(getResizedBitmap(R.drawable.damage_buff, GameParams.BUFF_SIZE, GameParams.BUFF_SIZE)))
                        }
                        1 -> {
                            buffs.add(SpeedBuff(getResizedBitmap(R.drawable.speed_buff, GameParams.BUFF_SIZE, GameParams.BUFF_SIZE)))
                        }
                        2 -> {
                            buffs.add(FrequencyBuff(getResizedBitmap(R.drawable.frequency_buff, GameParams.BUFF_SIZE, GameParams.BUFF_SIZE)))
                        }
                        else -> {}
                    }
                }
            }
        }


    }

    fun pauseJobs() {
        paused = true
        runBlocking {
            generatingEnemyJob?.cancelAndJoin()
            shottingJob?.cancelAndJoin()
        }

    }


    fun finish(){
        thread.setRunning(false)
        thread.join()
        runBlocking {
            generatingEnemyJob?.cancelAndJoin()
        }
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



}