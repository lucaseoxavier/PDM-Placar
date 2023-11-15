package com.pdm.placar_compose.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SettingsViewModel: ViewModel() {
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

    }
}