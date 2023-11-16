package com.pdm.placar_compose.viewmodels

import android.app.Application
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.pdm.placar_compose.MainActivity

class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    private val sharedPreferences = application.getSharedPreferences(MainActivity.SHARED_PREFERENCES, Context.MODE_PRIVATE)

    val title = mutableStateOf("")
    val team1Name = mutableStateOf("")
    val team2Name = mutableStateOf("")
    val extraTime = mutableStateOf("")

    fun onTitleChange(newValue: String) {
        title.value = newValue
    }

    fun onTeam1NameChange(newValue: String) {
        team1Name.value = newValue
    }

    fun onTeam2NameChange(newValue: String) {
        team2Name.value = newValue
    }

    fun onExtraTimeChange(newValue: String) {
        extraTime.value = newValue
    }

    fun onSaveClick() {
        sharedPreferences.edit()
            .putString(MATCH_NAME, title.value)
            .putString(TEAM_1_NAME, team1Name.value)
            .putString(TEAM_2_NAME, team2Name.value)
            .putString(EXTRA_TIME, extraTime.value)
            .apply()
    }

    fun loadSettings() {
        title.value = sharedPreferences.getString(MATCH_NAME, "") ?: ""
        team1Name.value = sharedPreferences.getString(TEAM_1_NAME, "") ?: ""
        team2Name.value = sharedPreferences.getString(TEAM_2_NAME, "") ?: ""
        extraTime.value = sharedPreferences.getString(EXTRA_TIME, "") ?: ""
    }

    companion object {
        const val MATCH_NAME = "MATCH_NAME"
        const val TEAM_1_NAME = "TEAM_1_NAME"
        const val TEAM_2_NAME = "TEAM_2_NAME"
        const val EXTRA_TIME = "EXTRA_TIME"
    }
}