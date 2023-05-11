package com.jiafuchen.spacy.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.jiafuchen.spacy.databinding.ActivityGameBinding
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.runBlocking

class GameActivity : AppCompatActivity() {

    lateinit var binding: ActivityGameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onResume() {
        super.onResume()
        if(binding.gameView.generatingEnemyJob?.isActive == false){
            binding.gameView.startJobs()
        }
    }

    override fun onPause() {
        super.onPause()
        if(binding.gameView.generatingEnemyJob?.isActive == true){
            binding.gameView.pauseJobs()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if(binding.gameView.generatingEnemyJob?.isActive == true){
            binding.gameView.finish()
        }
    }

    override fun onBackPressed() {
        val setIntent = Intent(Intent.ACTION_MAIN)
        setIntent.addCategory(Intent.CATEGORY_HOME)
        setIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(setIntent)
    }


}