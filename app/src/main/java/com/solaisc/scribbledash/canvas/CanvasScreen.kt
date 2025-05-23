package com.solaisc.scribbledash.canvas

import android.util.Log
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.solaisc.scribbledash.ui.components.CloseSection
import com.solaisc.scribbledash.ui.theme.BgGradient1
import com.solaisc.scribbledash.ui.theme.BgGradient2
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.util.fastForEach
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.solaisc.scribbledash.R
import com.solaisc.scribbledash.canvas.util.drawLine
import com.solaisc.scribbledash.canvas.util.drawPath
import com.solaisc.scribbledash.result.ResultScreen
import com.solaisc.scribbledash.ui.theme.Success
import kotlinx.coroutines.delay

@Composable
fun CanvasScreen(
    navController: NavController,
    imageResources: List<List<PathData>>,
    level: String
) {
    val refStrokeWidth = when (level) {
        "Beginner" -> {
            150f
        }
        "Challenging" -> {
            70f
        }
        else -> {
            40f
        }
    }

    val image by remember { mutableStateOf(imageResources.random()) }
    var timeLeft by remember { mutableIntStateOf(3) }

    val viewModel = viewModel<CanvasViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    var canvasSize by remember { mutableStateOf(IntSize.Zero) }

    viewModel.setPathsToCanvas(image, canvasSize)

    LaunchedEffect(timeLeft, state.canDraw) {
        if (!state.canDraw && timeLeft >= 1) {
            delay(1000)
            timeLeft -= 1
        } else {

            viewModel.onAction(CanvasAction.OnTimerRunning(true))
        }
    }

    if (state.score != null) {
        ResultScreen(
            navController,
            state,
            canvasSize
        )
    } else {
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
                    text = if (state.canDraw) { "Time to draw!" } else { "Ready, set..." },
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
                    if (state.canDraw) {
                        DrawingCanvas(
                            paths = state.paths,
                            currentPath = state.currentPath,
                            canDraw = state.canDraw,
                            onSizeChanged = {
                                canvasSize = it
                            },
                            onAction = viewModel::onAction
                        )
                    } else {
                        PreviewScreen(
                            parsedPath = state.samplePaths,
                            onSizeChanged = {
                                canvasSize = it
                            },
                        )
                    }

                }
                Spacer(Modifier.height(8.dp))
                Text(
                    text = if (state.canDraw) { "Your Drawing" } else { "Example" },
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(48.dp))
                CountDownTimer(timeLeft)
            }
        }


        CloseSection(navController)
        if (state.canDraw) {
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
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row {
                        IconButton(
                            onClick = {
                                viewModel.onAction(action = CanvasAction.OnUndoClick)
                            },
                            enabled = if (state.undoPaths.isEmpty()) {
                                false
                            } else true,
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
                                tint = if (state.undoPaths.isEmpty()) {
                                    MaterialTheme.colorScheme.onSurface
                                } else MaterialTheme.colorScheme.onBackground
                            )
                        }
                        Spacer(Modifier.width(8.dp))
                        IconButton(
                            onClick = {
                                viewModel.onAction(action = CanvasAction.OnRedoClick)
                            },
                            enabled = if (state.redoPaths.isEmpty()) {
                                false
                            } else true,
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
                                tint = if (state.redoPaths.isEmpty()) {
                                    MaterialTheme.colorScheme.onSurface
                                } else MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }

                    Button(
                        onClick = {
                            //viewModel.onAction(action = CanvasAction.OnClearCanvasClick)
                            viewModel.onAction(
                                CanvasAction.OnCompareDrawings(
                                    canvasSize.width,
                                    canvasSize.height,
                                    refStrokeWidth
                                )
                            )
                        },
                        enabled = if (state.paths.isEmpty()) { false } else true,
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(3.dp, Color.White),
                        colors = ButtonColors(
                            containerColor = Success,
                            contentColor = Color.White,
                            disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                            disabledContentColor = Color.White
                        )
                    ) {
                        Text(
                            text = "DONE",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.White,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PreviewScreen(
    parsedPath: List<PathData>,
    onSizeChanged: (IntSize) -> Unit,
    modifier: Modifier = Modifier
) {
    Canvas(
        modifier = modifier
            .clipToBounds()
            .onGloballyPositioned {
                onSizeChanged(it.size)
            }
            .size(320.dp)
            .border(width = 1.dp, color = BgGradient1, shape = RoundedCornerShape(24.dp))
            .clip(RoundedCornerShape(24.dp))

    ) {
        drawLine()

        parsedPath.fastForEach { pathData ->
            drawPath(
                path = pathData.path,
                color = Color.Black
            )
            Log.d("Check preview path data", pathData.path.toString())
        }
    }
}

@Composable
fun DrawingCanvas(
    paths: List<PathData>,
    currentPath: PathData?,
    canDraw: Boolean,
    onSizeChanged: (IntSize) -> Unit,
    onAction: (CanvasAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Canvas(
        modifier = modifier
            .clipToBounds()
            .onGloballyPositioned {
                onSizeChanged(it.size)
            }
            .size(320.dp)
            .border(width = 1.dp, color = BgGradient1, shape = RoundedCornerShape(24.dp))
            .clip(RoundedCornerShape(24.dp))
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

        if (canDraw) {
            paths.fastForEach { pathData ->
                drawPath(
                    path = pathData.path,
                    color = pathData.color
                )
            }
            currentPath?.let {
                drawPath(
                    path = it.path
                )
            }
        }
    }
}

@Composable
fun CountDownTimer(
    timeLeft: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = timeLeft.toString(),
            style = MaterialTheme.typography.headlineSmall,
            color = if (timeLeft >= 1) { MaterialTheme.colorScheme.onBackground } else Color.Transparent
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = if (timeLeft > 1) { "seconds left" } else "second left",
            style = MaterialTheme.typography.headlineSmall,
            color = if (timeLeft >= 1) { MaterialTheme.colorScheme.onBackground } else Color.Transparent
        )
    }
}