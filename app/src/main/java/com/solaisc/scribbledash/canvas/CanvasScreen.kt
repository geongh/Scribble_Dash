package com.solaisc.scribbledash.canvas

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.solaisc.scribbledash.difficulty.DifficultyScreen
import com.solaisc.scribbledash.ui.components.CloseSection
import com.solaisc.scribbledash.ui.theme.BgGradient1
import com.solaisc.scribbledash.ui.theme.BgGradient2
import com.solaisc.scribbledash.ui.theme.ScribbleDashTheme
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsEndWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.util.fastForEach
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import com.solaisc.scribbledash.R
import com.solaisc.scribbledash.ui.theme.Success
import kotlin.math.abs

@Composable
fun CanvasScreen(
    navController: NavController
) {
    val viewModel = viewModel<CanvasViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

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
        ) {
            Text(
                text = "Time to draw!",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(24.dp))
                    .clip(RoundedCornerShape(24.dp))
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                DrawingCanvas(
                    paths = state.paths,
                    currentPath = state.currentPath,
                    onAction = viewModel::onAction,
                    modifier = Modifier
                        .size(320.dp)
                        .border(
                            width = 1.dp,
                            color = BgGradient2,
                            shape = RoundedCornerShape(24.dp)
                        )
                        .background(color = Color.White, shape = RoundedCornerShape(24.dp))
                )
            }

        }
    }
    CloseSection(navController)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 48.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            IconButton(
                onClick = {
                    viewModel.onAction(action =  CanvasAction.OnUndoClick)
                },
                enabled = if (state.undoPaths.isEmpty()) { false } else true,
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = if (state.undoPaths.isEmpty()) {
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                        } else MaterialTheme.colorScheme.onSurface,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clip(RoundedCornerShape(12.dp))

            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.reply),
                    contentDescription = null,
                    tint = if (state.undoPaths.isEmpty()) { MaterialTheme.colorScheme.onSurface } else MaterialTheme.colorScheme.onBackground
                )
            }
            IconButton(
                onClick = {
                    viewModel.onAction(action =  CanvasAction.OnRedoClick)
                },
                enabled = if (state.redoPaths.isEmpty()) { false } else true,
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = if (state.redoPaths.isEmpty()) {
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                        } else MaterialTheme.colorScheme.onSurface,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clip(RoundedCornerShape(12.dp))

            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.forward),
                    contentDescription = null,
                    tint = if (state.redoPaths.isEmpty()) { MaterialTheme.colorScheme.onSurface } else MaterialTheme.colorScheme.onBackground
                )
            }
            Button(
                onClick = {
                    viewModel.onAction(action = CanvasAction.OnClearCanvasClick)
                },
                enabled = if (state.paths.isEmpty()) { false } else true,
                border = BorderStroke(3.dp, Color.White),
                colors = ButtonColors(
                    containerColor = Success,
                    contentColor = Color.White,
                    disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    disabledContentColor = Color.White
                ),
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "CLEAR CANVAS",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.White,
                )
            }
        }
    }
}

@Composable
fun DrawingCanvas(
    paths: List<PathData>,
    currentPath: PathData?,
    onAction: (CanvasAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Canvas(
        modifier = modifier
            .clipToBounds()
            .background(Color.White)
            .pointerInput(true) {
                detectDragGestures(
                    onDragStart = {
                        onAction(CanvasAction.OnNewPathStart)
                    },
                    onDragEnd = {
                        onAction(CanvasAction.OnPathEnd)
                    },
                    onDrag = { change, _ ->
                        onAction(CanvasAction.OnDraw(change.position))
                    },
                    onDragCancel = {
                        onAction(CanvasAction.OnPathEnd)
                    }
                )
            }
    ) {
        drawLine()

        paths.fastForEach { pathData ->
            drawPath(
                path = pathData.path,
                color = pathData.color
            )
        }
        currentPath?.let {
            drawPath(
                path = it.path,
                color = it.color
            )
        }
    }
}

private fun DrawScope.drawPath(
    path: List<Offset>,
    color: Color = Color.Black,
    thickness: Float = 10f
) {
    val smoothedPath = Path().apply {
        if (path.isNotEmpty()) {
            moveTo(path.first().x, path.first().y)

            val smoothness = 5
            for (i in 1 .. path.lastIndex) {
                val from = path[i-1]
                val to = path[i]
                val dx = abs(from.x - to.x)
                val dy = abs(from.y - to.y)
                if (dx >= smoothness || dy >= smoothness) {
                    quadraticTo(
                        x1 = (from.x + to.x) / 2f,
                        y1 = (from.y + to.y) / 2f,
                        x2 = to.x,
                        y2 = to.y
                    )
                }
            }
        }
    }
    drawPath(
        path = smoothedPath,
        color = color,
        style = Stroke(
            width = thickness,
            cap = StrokeCap.Round,
            join = StrokeJoin.Round
        )
    )
}

private fun DrawScope.drawLine() {
    // 1st vertical line
    drawLine(
        color = BgGradient1,
        start = Offset(
            x = size.width * (1 / 3f),
            y = 0f
        ),
        end = Offset(
            x = size.width * (1 / 3f),
            y = size.height
        ),
        strokeWidth = 3.dp.toPx(),
        cap = StrokeCap.Round
    )

    // 2nd vertical line
    drawLine(
        color = BgGradient1,
        start = Offset(
            x = size.width * (2 / 3f),
            y = 0f
        ),
        end = Offset(
            x = size.width * (2 / 3f),
            y = size.height
        ),
        strokeWidth = 3.dp.toPx(),
        cap = StrokeCap.Round
    )

    // 1st horizontal line
    drawLine(
        color = BgGradient1,
        start = Offset(
            x = 0f,
            y = size.height * (1 / 3f)
        ),
        end = Offset(
            x = size.width,
            y = size.height * (1 / 3f)
        ),
        strokeWidth = 3.dp.toPx(),
        cap = StrokeCap.Round
    )

    // 2nd horizontal line
    drawLine(
        color = BgGradient1,
        start = Offset(
            x = 0f,
            y = size.height * (2 / 3f)
        ),
        end = Offset(
            x = size.width,
            y = size.height * (2 / 3f)
        ),
        strokeWidth = 3.dp.toPx(),
        cap = StrokeCap.Round
    )
}

@Preview(showBackground = true)
@Composable
fun CanvasScreenPreview() {
    ScribbleDashTheme {

    }
}