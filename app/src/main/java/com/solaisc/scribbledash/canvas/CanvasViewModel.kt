package com.solaisc.scribbledash.canvas

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class CanvasState(
    val currentPath: PathData? = null,
    val paths: List<PathData> = emptyList(),
    val redoPaths: List<PathData> = emptyList(),
    val undoPaths: List<PathData> = emptyList()
)

data class PathData(
    val id: String,
    val color: Color,
    val path: List<Offset>
)

sealed interface CanvasAction {
    data object OnNewPathStart: CanvasAction
    data class OnDraw(val offset: Offset): CanvasAction
    data object OnPathEnd: CanvasAction
    data object OnClearCanvasClick: CanvasAction
    data object OnRedoClick: CanvasAction
    data object OnUndoClick: CanvasAction
}

class CanvasViewModel : ViewModel() {
    private val _state = MutableStateFlow(CanvasState())
    val state = _state.asStateFlow()

    fun onAction(action: CanvasAction) {
        when(action) {
            CanvasAction.OnClearCanvasClick -> clearCanvas()
            is CanvasAction.OnDraw -> onDraw(action.offset)
            CanvasAction.OnNewPathStart -> onPathStart()
            CanvasAction.OnPathEnd -> onPathEnd()
            CanvasAction.OnRedoClick -> redo()
            CanvasAction.OnUndoClick -> undo()
        }
    }

    private fun redo() {
        if (state.value.redoPaths.isNotEmpty()) {
            val lastRedoPathData = state.value.redoPaths.last()

            if (state.value.undoPaths.size == 5) {
                val removeOldestUndo = state.value.undoPaths.first()

                _state.update { it.copy(
                    undoPaths = it.undoPaths - removeOldestUndo
                ) }
            }

            _state.update {
                it.copy(
                    paths = it.paths + lastRedoPathData,
                    undoPaths = it.undoPaths + lastRedoPathData,
                    redoPaths = it.redoPaths - lastRedoPathData
                )
            }
        }
    }

    private fun undo() {
        if (state.value.undoPaths.isNotEmpty()) {
            val lastPathData = state.value.paths.last()

            if (state.value.redoPaths.size == 5) {
                val removeOldestRedo = state.value.redoPaths.first()

                _state.update { it.copy(
                    redoPaths = it.redoPaths - removeOldestRedo
                ) }
            }

            _state.update {
                it.copy(
                    paths = it.paths - lastPathData,
                    undoPaths = it.undoPaths - lastPathData,
                    redoPaths = it.redoPaths + lastPathData
                )
            }
        }
    }

    private fun clearCanvas() {
        if (state.value.paths.isNotEmpty()) {
            _state.update {
                it.copy(
                    currentPath = null,
                    paths = emptyList(),
                    undoPaths = emptyList(),
                    redoPaths = emptyList()
                )
            }
        }
    }

    private fun onPathStart() {
        _state.update { it.copy(
            currentPath = PathData(
                id = System.currentTimeMillis().toString(),
                color = Color.Black,
                path = emptyList()
            )
        ) }
    }

    private fun onDraw(offset: Offset) {
        val currentPathData = state.value.currentPath ?: return
        _state.update { it.copy(
            currentPath = currentPathData.copy(
                path = currentPathData.path + offset
            )
        ) }
    }

    private fun onPathEnd() {
        val currentPathData = state.value.currentPath ?: return

        if (state.value.undoPaths.size == 5) {
            val removeOldestUndo = state.value.undoPaths.first()

            _state.update { it.copy(
              undoPaths = it.undoPaths - removeOldestUndo
            ) }
        }

        _state.update { it.copy(
            currentPath = null,
            paths = it.paths + currentPathData,
            undoPaths = it.undoPaths + currentPathData
        ) }
    }
}
