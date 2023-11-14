package com.pdm.placar_compose

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import com.pdm.placar_compose.ui.theme.PlacarComposeTheme

@Preview
@Composable
fun ScoreboardScreenPreview() {
    PlacarComposeTheme {
    }
}

@Composable
fun ScoreboardScreen(
    viewModel: ScoreboardViewModel,
) {
    ScoreboardScreen(
        team1Name = viewModel.team1Name.value,
        team2Name = viewModel.team2Name.value,
        leftScore = viewModel.leftScore.value,
        rightScore = viewModel.rightScore.value,
        onLeftClick = viewModel::onLeftClick,
        onRightClick = viewModel::onRightClick,
    )
}

@Composable
fun ScoreboardScreen(
    team1Name: String,
    team2Name: String,
    leftScore: Int,
    rightScore: Int,
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
                    text = leftScore.toString(),
                    fontSize = 100.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = team1Name,
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
                    text = rightScore.toString(),
                    fontSize = 100.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = team2Name,
                )
            }
        }
    }
}