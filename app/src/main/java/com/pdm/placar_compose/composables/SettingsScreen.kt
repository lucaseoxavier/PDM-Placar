package com.pdm.placar_compose.composables

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pdm.placar_compose.ui.theme.ScoreboardTheme
import com.pdm.placar_compose.viewmodels.SettingsViewModel

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    ScoreboardTheme {
        SettingsScreen(
            title = "",
            team1Name = "",
            team2Name = "",
            extraTime = "",
            onTitleChange = { },
            onTeam1NameChange = { },
            onTeam2NameChange = { },
            onExtraTimeChange = { },
            onSaveClick = { },
        )
    }
}

@Composable
fun SettingsScreen(
    paddingValues: PaddingValues,
    viewModel: SettingsViewModel
) {
    Column(modifier = Modifier.padding(paddingValues)) {
        SettingsScreen(
            title = viewModel.title.value,
            team1Name = viewModel.team1Name.value,
            team2Name = viewModel.team2Name.value,
            extraTime = viewModel.extraTime.value,
            onTitleChange = viewModel::onTitleChange,
            onTeam1NameChange = viewModel::onTeam1NameChange,
            onTeam2NameChange = viewModel::onTeam2NameChange,
            onExtraTimeChange = viewModel::onExtraTimeChange,
            onSaveClick = viewModel::onSaveClick,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    title: String,
    team1Name: String,
    team2Name: String,
    extraTime: String,
    onTitleChange: (String) -> Unit,
    onTeam1NameChange: (String) -> Unit,
    onTeam2NameChange: (String) -> Unit,
    onExtraTimeChange: (String) -> Unit,
    onSaveClick: () -> Unit,
) {
    val activity = LocalContext.current as Activity
    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
    ) {
        Text(text = "Configurações", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        OutlinedTextField(
            label = { Text(text = "Título") },
            value = title,
            onValueChange = onTitleChange,
            modifier = Modifier.fillMaxWidth(),
        )
        OutlinedTextField(
            label = { Text(text = "Time 1") },
            value = team1Name,
            onValueChange = onTeam1NameChange,
            modifier = Modifier.fillMaxWidth(),
        )
        OutlinedTextField(
            label = { Text(text = "Time 2") },
            value = team2Name,
            onValueChange = onTeam2NameChange,
            modifier = Modifier.fillMaxWidth(),
        )
        OutlinedTextField(
            label = { Text(text = "Tempo Extra") },
            value = extraTime,
            onValueChange = onExtraTimeChange,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = onSaveClick,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = "Salvar", color = Color.White, modifier = Modifier.padding(8.dp))
        }
    }
}