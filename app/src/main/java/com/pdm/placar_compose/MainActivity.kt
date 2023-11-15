package com.pdm.placar_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val scoreboardViewModel: ScoreboardViewModel by viewModels()
            App(scoreboardViewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    scoreboardViewModel: ScoreboardViewModel,
    ) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(onClick = { navController.navigate(ScoreboardRoute.Scoreboard.name) }) {
                        Icon(Icons.Filled.Home, contentDescription = "Navigate to Scoreboard screen")
                    }
                    IconButton(onClick = { navController.navigate(ScoreboardRoute.History.name) }) {
                        Icon(
                            Icons.Filled.List,
                            contentDescription = "Navigate to History screen",
                        )
                    }
                    IconButton(onClick = { navController.navigate(ScoreboardRoute.Configuration.name) }) {
                        Icon(
                            Icons.Filled.Settings,
                            contentDescription = "Navigate to Configuration screen",
                        )
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { /* do something */ },
                        containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                    ) {
                        Icon(Icons.Filled.Refresh, "Undo last point")
                    }
                }
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = ScoreboardRoute.Scoreboard.name,
        ) {
            composable(ScoreboardRoute.Scoreboard.name) {
                ScoreboardScreen(
                    paddingValues = paddingValues,
                    viewModel = scoreboardViewModel,
                )
            }
            composable(ScoreboardRoute.History.name) {
            }
            composable(ScoreboardRoute.Configuration.name) {
            }
        }
    }
}

enum class ScoreboardRoute {
    Scoreboard,
    History,
    Configuration
}