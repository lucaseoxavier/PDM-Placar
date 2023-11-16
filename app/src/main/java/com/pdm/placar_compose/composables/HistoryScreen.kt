package com.pdm.placar_compose.composables

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pdm.placar_compose.Game
import com.pdm.placar_compose.Team
import com.pdm.placar_compose.ui.theme.ScoreboardTheme
import com.pdm.placar_compose.viewmodels.HistoryViewModel

@Preview
@Composable
fun HistoryScreenPreview() {
    ScoreboardTheme {
        GameItem(
            game = Game(
                title = "Game",
                team1 = Team("Team 1"),
                team2 = Team("Team 2")
            )
        )
    }
}

@Composable
fun HistoryScreen(
    paddingValues: PaddingValues,
    viewModel: HistoryViewModel
) {
    val activity = LocalContext.current as Activity
    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    val context = LocalContext.current

    val games = remember { mutableStateOf(viewModel.getPreviousGames(context)) }

    LazyColumn(modifier = Modifier
        .fillMaxWidth()
        .padding(paddingValues)) {
        items(games.value) {game ->
            GameItem(game)
        }
    }
}

@Composable
fun GameItem(game: Game) {
    val team1 = game.team1
    val team2 = game.team2

    Column(
        Modifier
            .background(Color.White, RoundedCornerShape(12.dp))
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Text(text = game.title)
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(text = team1.name)
            Text(text = team1.score.toString())
            Text(text = "Vs")
            Text(text = team2.name)
            Text(text = team2.score.toString())
        }
    }
}