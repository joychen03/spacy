package com.jiafuchen.spacy.ui.constants

import com.jiafuchen.spacy.R

object GameParams {


    var PLAYER_IMAGE = R.drawable.space_ship
    var PLAYER_SPEED = 20
    var PLAYER_SIZE = 200
    var PLAYER_BULLET_FREQUENCY = 1000
    var PLAYER_BULLET_DAMAGE = 1
    var PLAYER_BULLET_SPEED = 30
    var PLAYER_MAX_BULLET_SPEED = 60
    var PLAYER_MAX_BULLET_DAMAGE = 5
    var PLAYER_MAX_BULLET_FREQUENCY = 100

    var ENEMY_LIFE = 20
    var ENEMY_SIZE = 200
    var MAX_ENEMIES = 10
    var ENEMY_MAX_SPEED = 10
    var ENEMY_MIN_SPEED = 5

    var BUFF_SIZE = 200
    var BUFF_DAMAGE_INCREASE = 1
    var BUFF_SPEED_INCREASE = 5
    var BUFF_FREQUENCY_INCREASE = 100

    var BACKGROUND_IMAGE = R.drawable.background

    val ENEMIES_IMAGES = mutableListOf(
        R.drawable.alient_1,
        R.drawable.alient_2,
        R.drawable.alient_3,
        R.drawable.alient_4,
        R.drawable.alient_5,
        R.drawable.alient_6,
        R.drawable.alient_7,
    )

}