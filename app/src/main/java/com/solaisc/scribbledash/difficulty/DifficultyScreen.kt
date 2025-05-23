package com.solaisc.scribbledash.difficulty

import android.graphics.Path
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.solaisc.scribbledash.R
import com.solaisc.scribbledash.difficulty.components.DiffChoice
import com.solaisc.scribbledash.ui.components.CloseSection
import com.solaisc.scribbledash.ui.theme.BgGradient1
import com.solaisc.scribbledash.ui.theme.BgGradient2
import com.solaisc.scribbledash.ui.theme.ScribbleDashTheme

@Composable
fun DifficultyScreen(
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        BgGradient1,
                        BgGradient2
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .offset(0.dp, (-80).dp)
        ) {
            Text(
                text = "Start drawing!",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Choose a difficulty setting",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                DiffChoice(
                    image = painterResource(R.drawable.beginner_off),
                    text = "Beginner",
                    onClick = {
                        navController.navigate("canvas_screen?level=Beginner") {
                            popUpTo("canvas_screen") {
                                inclusive = true
                            }
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .offset(0.dp, 16.dp)
                )
                DiffChoice(
                    image = painterResource(R.drawable.challenging_off),
                    text = "Challenging",
                    onClick = {
                        navController.navigate("canvas_screen?level=Challenging") {
                            popUpTo("canvas_screen") {
                                inclusive = true
                            }
                        }
                    },
                    modifier = Modifier.weight(1f)
                )
                DiffChoice(
                    image = painterResource(R.drawable.master_off),
                    text = "Master",
                    onClick = {
                        navController.navigate("canvas_screen?level=Master") {
                            popUpTo("canvas_screen") {
                                inclusive = true
                            }
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .offset(0.dp, 16.dp)
                )
            }
        }
    }
    CloseSection(navController)
}

@Preview(showBackground = true)
@Composable
fun DifficultyScreenPreview() {
    ScribbleDashTheme {

    }
}