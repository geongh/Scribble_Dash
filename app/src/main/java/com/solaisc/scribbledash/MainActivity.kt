package com.solaisc.scribbledash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.solaisc.scribbledash.canvas.CanvasScreen
import com.solaisc.scribbledash.difficulty.DifficultyScreen
import com.solaisc.scribbledash.home.HomeScreen
import com.solaisc.scribbledash.result.ResultScreen
import com.solaisc.scribbledash.ui.theme.ScribbleDashTheme

class MainActivity : ComponentActivity() {
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getPathFromResource(this)

        enableEdgeToEdge()

        setContent {
            ScribbleDashTheme {
                val state = viewModel.images.collectAsStateWithLifecycle().value

                val navController = rememberNavController()

                NavHost(
                    navController = navController, startDestination = "main_screen"
                ) {
                    composable("main_screen") {
                        MainScreen(navController)
                    }
                    composable("difficulty_screen") {
                        DifficultyScreen(navController)
                    }
                    composable(
                        route = "canvas_screen?level={level}",
                        arguments = listOf(
                            navArgument(
                                name = "level"
                            ) {
                                type = NavType.StringType
                                defaultValue = "Beginner"
                            }
                        )
                    ) { backStackEntry ->
                        val level = backStackEntry.arguments?.getString("level") ?: "Beginner"

                        CanvasScreen(
                            navController = navController,
                            imageResources = state,
                            level = level
                        )
                    }
                }

            }
        }
    }
}