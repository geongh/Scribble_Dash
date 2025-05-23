package com.solaisc.scribbledash.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.solaisc.scribbledash.R
import com.solaisc.scribbledash.ui.theme.ScribbleDashTheme
import com.solaisc.scribbledash.ui.theme.Success

@Composable
fun MenuItem(
    borderColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .border(8.dp, borderColor, RoundedCornerShape(16.dp))
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.padding(start = 24.dp)
            ) {
                Text(
                    text = "One Round",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Wonder",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Image(
                painter = painterResource(R.drawable.one_round_wonder),
                contentDescription = null,
                modifier = Modifier
                    .width(160.dp)
                    .height(112.dp)
            )
        }


    }
}

@Preview(showBackground = true)
@Composable
fun MenuItemPreview() {
    ScribbleDashTheme {
        MenuItem(
            borderColor = Success,
            onClick = {

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}