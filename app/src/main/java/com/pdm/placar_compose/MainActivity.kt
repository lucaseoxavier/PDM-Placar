package com.pdm.placar_compose

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.pdm.placar_compose.composables.HistoryScreen
import com.pdm.placar_compose.composables.ScoreboardScreen
import com.pdm.placar_compose.composables.SettingsScreen
import com.pdm.placar_compose.ui.theme.ScoreboardTheme
import com.pdm.placar_compose.viewmodels.HistoryViewModel
import com.pdm.placar_compose.viewmodels.ScoreboardViewModel
import com.pdm.placar_compose.viewmodels.SettingsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val scoreboardViewModel: ScoreboardViewModel by viewModels()
            val historyViewModel: HistoryViewModel by viewModels()
            val settingsViewModel: SettingsViewModel by viewModels()

            ScoreboardTheme {
                App(scoreboardViewModel, historyViewModel, settingsViewModel, applicationContext)
            }
        }
    }

    companion object {
        const val SHARED_PREFERENCES = "sharedPreferences"
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    scoreboardViewModel: ScoreboardViewModel,
    historyViewModel: HistoryViewModel,
    settingsViewModel: SettingsViewModel,
    context: Context,
) {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

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
                    IconButton(onClick = { navController.navigate(ScoreboardRoute.Settings.name) }) {
                        Icon(
                            Icons.Filled.Settings,
                            contentDescription = "Navigate to Settings screen",
                        )
                    }
                    if (currentRoute == ScoreboardRoute.Scoreboard.name) {
                        IconButton(onClick = { scoreboardViewModel.saveGameResult() }) {
                            Icon(
                                Icons.Filled.Done,
                                contentDescription = "Save current game",
                            )
                        }
                    }
                    if (currentRoute == ScoreboardRoute.History.name) {
                        IconButton(onClick = { historyViewModel.deletePreviousGames(context) }) {
                            Icon(
                                Icons.Filled.Delete,
                                contentDescription = "Delete previous games",
                            )
                        }
                    }
                },
                floatingActionButton = {
                    if (currentRoute == ScoreboardRoute.Scoreboard.name) {
                        FloatingActionButton(
                            onClick = { scoreboardViewModel.undoLastScore() },
                            containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                            elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                        ) {
                            Icon(Icons.Filled.Refresh, "Undo last point")
                        }
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
                HistoryScreen(
                    paddingValues = paddingValues,
                    viewModel = historyViewModel,
                )
            }
            composable(ScoreboardRoute.Settings.name) {
                SettingsScreen(
                    paddingValues = paddingValues,
                    viewModel = settingsViewModel,
                    navController = navController,
                )
            }
        }
    }
}

enum class ScoreboardRoute {
    Scoreboard,
    History,
    Settings
}