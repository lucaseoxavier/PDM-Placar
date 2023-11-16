package com.pdm.placar_compose.viewmodels

import com.pdm.placar_compose.TimerManager
import android.app.Application
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.pdm.placar_compose.Game
import com.pdm.placar_compose.MainActivity
import com.pdm.placar_compose.Team
import java.io.ByteArrayOutputStream
import java.io.ObjectOutputStream
import java.nio.charset.StandardCharsets
import java.util.Stack

class ScoreboardViewModel(application: Application) : AndroidViewModel(application) {
    private val sharedPreferences = application.getSharedPreferences(MainActivity.SHARED_PREFERENCES, Context.MODE_PRIVATE)

    val game = mutableStateOf(Game("Match", Team("Time 1"), Team("Time 2")))

    private val scoreStack: Stack<String> = Stack()
    val timerManager = TimerManager(
        secondsState = mutableStateOf(0),
        isTimerRunningState = mutableStateOf(false),
    )

    fun onLeftClick() {
        val team = game.value.team1
        game.value = game.value.copy(team1 = team.copy(score = team.score + 1))
        scoreStack.push(team.name)
    }

    fun onRightClick() {
        val team = game.value.team2
        game.value = game.value.copy(team2 = team.copy(score = team.score + 1))
        scoreStack.push(team.name)
    }

    fun undoLastScore() {
        val team1 = game.value.team1
        val team2 = game.value.team2

        if (scoreStack.isNotEmpty()) {
            val lastScoredTeam = scoreStack.pop()
            when (lastScoredTeam) {
                team1.name -> {
                    game.value = game.value.copy(team1 = Team(team1.name, team1.score - 1))
                }
                team2.name -> {
                    game.value = game.value.copy(team2 = Team(team2.name, team2.score - 1))
                }
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

    fun updateGame() {
        game.value.title = sharedPreferences.getString(SettingsViewModel.MATCH_NAME, game.value.title)!!
        game.value.team1.name = sharedPreferences.getString(SettingsViewModel.TEAM_1_NAME, game.value.team1.name)!!
        game.value.team2.name = sharedPreferences.getString(SettingsViewModel.TEAM_2_NAME, game.value.team2.name)!!
        val extraTime = sharedPreferences.getString(SettingsViewModel.EXTRA_TIME, "0")
        if (!extraTime.isNullOrEmpty()) {
            timerManager.setupExtraTime(extraTime.toInt())
        } else {
            timerManager.setupExtraTime(0)
        }
    }
}