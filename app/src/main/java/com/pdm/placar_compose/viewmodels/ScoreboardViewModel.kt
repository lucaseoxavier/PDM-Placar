package com.pdm.placar_compose.viewmodels

import android.app.Application
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.pdm.placar_compose.Game
import com.pdm.placar_compose.MainActivity
import com.pdm.placar_compose.Team
import java.io.ByteArrayOutputStream
import java.io.ObjectOutputStream
import java.nio.charset.StandardCharsets
import java.util.Stack

class ScoreboardViewModel(application: Application) : AndroidViewModel(application) {
    private val sharedPreferences = application.getSharedPreferences(MainActivity.SHARED_PREFERENCES, Context.MODE_PRIVATE)

    private val scoreStack: Stack<Team> = Stack()
    val team1 = mutableStateOf(Team("Time 1"))
    val team2 = mutableStateOf(Team("Time 2"))
    val game = mutableStateOf(Game("Match", team1.value, team2.value))

    fun onLeftClick() {
        team1.value = team1.value.copy(score = team1.value.score + 1)
        scoreStack.push(team1.value)
    }

    fun onRightClick() {
        team2.value = team2.value.copy(score = team2.value.score + 1)
        scoreStack.push(team2.value)
    }

    fun undoLastScore() {
        if (scoreStack.isNotEmpty()) {
            when (scoreStack.pop()) {
                team1.value -> team1.value = team1.value.copy(score = team1.value.score - 1)
                team2.value -> team2.value = team2.value.copy(score = team2.value.score - 1)
            }
        }
    }

    fun saveGameResult() {
        val numMatches = sharedPreferences.getInt("numberMatch", 0) + 1
        sharedPreferences
            .edit()
            .putInt("numberMatch", numMatches)
            .apply()

        val byteArrayOutputStream = ByteArrayOutputStream()
        val objectOutputStream = ObjectOutputStream(byteArrayOutputStream)
        objectOutputStream.writeObject(game.value)

        sharedPreferences
            .edit()
            .putString("match$numMatches", byteArrayOutputStream.toString(StandardCharsets.ISO_8859_1.name()))
            .apply()
    }
}