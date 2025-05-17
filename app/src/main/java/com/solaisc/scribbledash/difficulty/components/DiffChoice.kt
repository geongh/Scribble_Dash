package com.solaisc.scribbledash.difficulty.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.solaisc.scribbledash.R
import com.solaisc.scribbledash.ui.theme.ScribbleDashTheme

@Composable
fun DiffChoice(
    image: Painter,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
        ) {
            Image(
                painter = image,
                contentDescription = null,
                modifier = Modifier
                    .size(128.dp)
                    .border(
                        0.dp, Color.Transparent, CircleShape
                    )
                    .clip(CircleShape)
                    .clickable {
                        onClick()
                    }

            )
            Text(
                text = text,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.offset(0.dp, (-12).dp)
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DiffChoicePreview() {
    ScribbleDashTheme {
        DiffChoice(
            image = painterResource(R.drawable.beginner_off),
            text = "Beginner",
            onClick = {

            }
        )
    }
}