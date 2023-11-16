package com.pdm.placar_compose.composables

import com.pdm.placar_compose.TimerManager
import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pdm.placar_compose.viewmodels.ScoreboardViewModel
import com.pdm.placar_compose.Team

@Composable
fun ScoreboardScreen(
    paddingValues: PaddingValues,
    viewModel: ScoreboardViewModel,
) {
    DisposableEffect(Unit) {
        viewModel.updateGame()
        onDispose { }
    }

    Column(modifier = Modifier.padding(paddingValues)) {
        ScoreboardContent(
            title = viewModel.game.value.title,
            team1 = viewModel.game.value.team1,
            team2 = viewModel.game.value.team2,
            onLeftClick = viewModel::onLeftClick,
            onRightClick = viewModel::onRightClick,
            timerManager = viewModel.timerManager,
        )
    }
}

@Composable
fun ScoreboardContent(
    title: String,
    team1: Team,
    team2: Team,
    onLeftClick: () -> Unit,
    onRightClick: () -> Unit,
    timerManager: TimerManager,
) {
    val activity = LocalContext.current as Activity
    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

    var expanded by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = timerManager.getFormattedTime(),
                fontSize = 25.sp,
            )

            Box(
                modifier = Modifier.padding(start = 110.dp)
            ) {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More"
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Iniciar") },
                        onClick = {
                            if (!timerManager.isTimerRunning()) {
                                timerManager.startTimer()
                            }
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Pausar") },
                        onClick = {
                            timerManager.stopTimer()
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Resetar") },
                        onClick = {
                            timerManager.resetTimer()
                            expanded = false
                        }
                    )
                }
            }
        }

        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )

        Row(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            // Left
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable {
                        onLeftClick.invoke()
                    }
            ) {
                Text(
                    text = team1.score.toString(),
                    fontSize = 100.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = team1.name,
                )
            }

            // Right
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable {
                        onRightClick.invoke()
                    }
            ) {
                Text(
                    text = team2.score.toString(),
                    fontSize = 100.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = team2.name,
                )
            }
        }
    }
}