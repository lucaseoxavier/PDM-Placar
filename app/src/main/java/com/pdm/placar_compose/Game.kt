package com.pdm.placar_compose

import java.io.Serializable

data class Game(
    var title: String,
    val team1: Team,
    val team2: Team,
) : Serializable

data class Team(
    var name: String,
    var score: Int = 0,
) : Serializable