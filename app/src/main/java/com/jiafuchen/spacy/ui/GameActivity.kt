package com.jiafuchen.spacy.ui

import android.content.Context
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.view.WindowManager
import androidx.core.content.getSystemService
import androidx.fragment.app.FragmentActivity
import com.jiafuchen.spacy.R
import java.lang.Exception

class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
    }
}