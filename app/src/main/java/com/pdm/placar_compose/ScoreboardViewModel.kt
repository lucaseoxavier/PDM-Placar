package com.pdm.placar_compose

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ScoreboardViewModel: ViewModel() {
    val team1Name = mutableStateOf("Time 1")
    val team2Name = mutableStateOf("Time 2")
    val leftScore = mutableStateOf(0)
    val rightScore = mutableStateOf(0)

    fun onLeftClick() {
        leftScore.value += 1
    }

    fun onRightClick() {
        rightScore.value += 1
    }
}