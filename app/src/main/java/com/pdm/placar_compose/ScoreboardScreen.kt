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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
    onLeftClick: () -> Unit,
    onRightClick: () -> Unit,
) {
    val activity = LocalContext.current as Activity
    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

    val leftScore = remember { mutableStateOf(0) }
    val rightScore = remember { mutableStateOf(0) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "00:00",
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
                        leftScore.value += 1
                        onLeftClick.invoke()
                    }
            ) {
                Text(
                    text = leftScore.value.toString(),
                    fontSize = 100.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Time 1",
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
                        rightScore.value += 1
                        onRightClick.invoke()
                    }
            ) {
                Text(
                    text = rightScore.value.toString(),
                    fontSize = 100.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Time 2",
                )
            }
        }
    }
}