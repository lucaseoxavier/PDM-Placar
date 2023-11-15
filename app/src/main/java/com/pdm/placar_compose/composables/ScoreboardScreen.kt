package com.pdm.placar_compose.composables

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pdm.placar_compose.viewmodels.ScoreboardViewModel
import com.pdm.placar_compose.Team
import com.pdm.placar_compose.ui.theme.ScoreboardTheme

@Preview
@Composable
fun ScoreboardScreenPreview() {
    ScoreboardTheme {
    }
}

@Composable
fun ScoreboardScreen(
    paddingValues: PaddingValues,
    viewModel: ScoreboardViewModel,
) {
    Column(modifier = Modifier.padding(paddingValues)) {
        ScoreboardScreen(
            team1 = viewModel.team1.value,
            team2 = viewModel.team2.value,
            onLeftClick = viewModel::onLeftClick,
            onRightClick = viewModel::onRightClick,
        )
    }
}

@Composable
fun ScoreboardScreen(
    team1: Team,
    team2: Team,
    onLeftClick: () -> Unit,
    onRightClick: () -> Unit,
) {
    val activity = LocalContext.current as Activity
    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 8.dp)
    ) {
        Text(
            text = "00:00",
            fontSize = 25.sp,
        )
        Row(
            modifier = Modifier
                .fillMaxSize()
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
                Spacer(modifier = Modifier.height(16.dp))
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
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = team2.name,
                )
            }
        }
    }
}