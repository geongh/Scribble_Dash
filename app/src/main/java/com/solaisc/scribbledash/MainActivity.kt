package com.solaisc.scribbledash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.solaisc.scribbledash.canvas.CanvasScreen
import com.solaisc.scribbledash.difficulty.DifficultyScreen
import com.solaisc.scribbledash.home.HomeScreen
import com.solaisc.scribbledash.statistic.StatisticScreen
import com.solaisc.scribbledash.ui.theme.BgGradient1
import com.solaisc.scribbledash.ui.theme.BgGradient2
import com.solaisc.scribbledash.ui.theme.ScribbleDashTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ScribbleDashTheme {
                /*Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }*/

                val navController = rememberNavController()
                NavHost(
                    navController = navController, startDestination = "home"
                ) {
                    navigation(
                        startDestination = "start_screen",
                        route = "home"
                    ) {
                        composable("start_screen") {
                            HomeScreen(navController)
                        }
                        composable("statistic_screen") {

                        }
                    }
                    composable("difficulty_screen") {
                        DifficultyScreen(navController)
                    }
                    composable("canvas_screen") {
                        CanvasScreen(navController)
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ScribbleDashTheme {
        Greeting("Android")
    }
}