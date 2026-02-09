package com.tasktracker.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tasktracker.data.model.Task
import com.tasktracker.ui.theme.Motion
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@Composable
fun TimelineView(
    tasks: List<Task>,
    currentDate: LocalDateTime,
    onTaskClick: (Task) -> Unit,
    onTaskMove: (Task, LocalTime) -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()
    val density = LocalDensity.current
    val hourHeight = 80.dp
    
    // Scroll to current time on first composition
    LaunchedEffect(Unit) {
        val currentHour = LocalDateTime.now().hour
        listState.scrollToItem(currentHour.coerceAtLeast(1) - 1)
    }
    
    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize()
        ) {
            // Generate 24 hours
            items(24) { hour ->
                TimelineHourSlot(
                    hour = hour,
                    tasks = tasks.filter { task ->
                        task.startTime?.hour == hour
                    },
                    isCurrentHour = hour == LocalDateTime.now().hour,
                    hourHeight = hourHeight,
                    onTaskClick = onTaskClick,
                    onTaskMove = onTaskMove
                )
            }
        }
    }
}

@Composable
private fun TimelineHourSlot(
    hour: Int,
    tasks: List<Task>,
    isCurrentHour: Boolean,
    hourHeight: Dp,
    onTaskClick: (Task) -> Unit,
    onTaskMove: (Task, LocalTime) -> Unit
) {
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    val outlineColor = MaterialTheme.colorScheme.outline
    val currentTimeColor = MaterialTheme.colorScheme.primary
    
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(hourHeight)
            .drawBehind {
                // Draw hour line
                drawLine(
                    color = if (isCurrentHour) currentTimeColor else outlineColor,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = if (isCurrentHour) 3f else 1f
                )
            }
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.Top
        ) {
            // Hour label
            Text(
                text = String.format("%02d:00", hour),
                style = MaterialTheme.typography.labelMedium,
                color = if (isCurrentHour) 
                    MaterialTheme.colorScheme.primary 
                else 
                    MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .width(60.dp)
                    .padding(top = 4.dp, end = 8.dp),
                textAlign = TextAlign.End
            )
            
            // Tasks area
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(end = 16.dp)
            ) {
                tasks.forEach { task ->
                    TaskTimelineItem(
                        task = task,
                        hourHeight = hourHeight,
                        onClick = { onTaskClick(task) },
                        onMove = { newTime -> onTaskMove(task, newTime) }
                    )
                }
            }
        }
    }
}

@Composable
private fun TaskTimelineItem(
    task: Task,
    hourHeight: Dp,
    onClick: () -> Unit,
    onMove: (LocalTime) -> Unit
) {
    val density = LocalDensity.current
    var offsetY by remember { mutableStateOf(0f) }
    
    val startTime = task.startTime ?: return
    val endTime = task.endTime ?: return
    
    // Calculate position and height
    val minuteOffset = startTime.minute
    val duration = task.durationMinutes ?: 60
    
    val topOffset = with(density) { (minuteOffset / 60f * hourHeight.toPx()).dp }
    val itemHeight = with(density) { (duration / 60f * hourHeight.toPx()).dp }
    
    val scale by animateFloatAsState(
        targetValue = if (offsetY != 0f) 1.05f else 1f,
        animationSpec = Motion.spring,
        label = "scale"
    )
    
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .offset(y = topOffset)
            .height(itemHeight.coerceAtLeast(40.dp))
            .padding(vertical = 2.dp)
    ) {
        TaskCard(
            task = task,
            onClick = onClick,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                }
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = { offsetY = 1f },
                        onDragEnd = { 
                            offsetY = 0f
                            // Calculate new time based on drag
                        },
                        onDrag = { change, dragAmount ->
                            change.consume()
                            offsetY += dragAmount.y
                        }
                    )
                }
        )
    }
}
