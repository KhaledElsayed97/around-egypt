package dev.khaled.aroundegypt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.khaled.aroundegypt.navigation.Screen
import dev.khaled.aroundegypt.ui.screens.ExperienceScreen
import dev.khaled.aroundegypt.ui.screens.HomeScreen
import dev.khaled.aroundegypt.ui.theme.AroundEgyptTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AroundEgyptTheme {
                AroundEgyptApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AroundEgyptApp() {
    val navController = rememberNavController()
    val viewModel = remember { MainViewModel() }

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToExperience = { placeId ->
                    viewModel.selectSight(placeId)
                },
                viewModel = viewModel
            )
        }
    }

    viewModel.selectedSightId.value?.let { sightId ->
        ExperienceScreen(
            sightId = sightId,
            onNavigateBack = { 
                viewModel.clearSelection()
            },
            viewModel = viewModel
        )
    }
}