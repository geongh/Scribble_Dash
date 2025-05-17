package com.solaisc.scribbledash.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.solaisc.scribbledash.R

@Composable
fun CloseSection(
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 36.dp, end = 16.dp),
        contentAlignment = Alignment.TopEnd
    ) {
        IconButton(
            onClick = {
                navController.navigate("home") {
                    popUpTo("home") {
                        inclusive = true
                    }
                }
            }
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.close),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.size(36.dp)
            )
        }
    }
}