package com.solaisc.scribbledash.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.solaisc.scribbledash.R
import com.solaisc.scribbledash.home.components.MenuItem
import com.solaisc.scribbledash.ui.theme.BgGradient1
import com.solaisc.scribbledash.ui.theme.BgGradient2
import com.solaisc.scribbledash.ui.theme.ScribbleDashTheme
import com.solaisc.scribbledash.ui.theme.Success

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Scaffold(
        bottomBar = {
           NavigationBar(
               containerColor = Color.White
           ) {
               IconButton(
                   onClick = {

                   },
                   modifier = Modifier.weight(1f)
               ) {
                   Icon(
                       imageVector = ImageVector.vectorResource(R.drawable.chart),
                       contentDescription = null
                   )

               }
               IconButton(
                   onClick = {
                       navController.navigate("start_screen") {
                           popUpTo("home") {
                               inclusive = true
                           }
                       }
                   },
                   modifier = Modifier.weight(1f)
               ) {
                   Icon(
                       imageVector = ImageVector.vectorResource(R.drawable.home),
                       contentDescription = null,
                       tint = MaterialTheme.colorScheme.primary
                   )

               }
           }
        },
        modifier = modifier
    ) { innerPadding ->
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
                )
                .padding(innerPadding),
            contentAlignment = Alignment.TopCenter
        ) {
            Text(
                text = "Scribble Dash",
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 32.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top =96.dp)
                    .padding(horizontal = 16.dp)
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
                    text = "Select game mode",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                MenuItem(
                    borderColor = Success,
                    onClick = {
                        navController.navigate("difficulty_screen") {
                            popUpTo("difficulty_screen") {
                                inclusive = true
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    ScribbleDashTheme {

    }
}