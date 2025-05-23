package com.solaisc.scribbledash.statistic

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.solaisc.scribbledash.R
import com.solaisc.scribbledash.statistic.components.StatisticItem
import com.solaisc.scribbledash.ui.theme.BgGradient1
import com.solaisc.scribbledash.ui.theme.BgGradient2

@Composable
fun StatisticScreen(
    navController: NavController,
    padding: PaddingValues
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
            )
            .padding(padding)
    ) {
        Text(
            text = "Statistic",
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 32.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 64.dp)
                .padding(horizontal = 16.dp)
        ) {
            StatisticItem(
               imagePainter = R.drawable.stat_hourglass,
               title = "Nothing to track.. for now",
               score = "0%"
            )
            Spacer(Modifier.height(12.dp))
            StatisticItem(
                imagePainter = R.drawable.stat_bolt,
                title = "Nothing to track.. for now",
                score = "0"
            )
        }
    }
}