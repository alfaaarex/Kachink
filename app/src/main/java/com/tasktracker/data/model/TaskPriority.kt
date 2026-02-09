package com.tasktracker.data.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Task priority levels with Material 3 Expressive colors and icons
 */
enum class TaskPriority(
    val displayName: String,
    val color: Color,
    val icon: ImageVector,
    val sortOrder: Int
) {
    HIGH(
        displayName = "High",
        color = Color(0xFFEF5350), // Vibrant red
        icon = Icons.Default.PriorityHigh,
        sortOrder = 0
    ),
    MEDIUM(
        displayName = "Medium",
        color = Color(0xFFFFA726), // Vibrant orange
        icon = Icons.Default.Remove,
        sortOrder = 1
    ),
    LOW(
        displayName = "Low",
        color = Color(0xFF66BB6A), // Vibrant green
        icon = Icons.Default.KeyboardArrowDown,
        sortOrder = 2
    ),
    NONE(
        displayName = "None",
        color = Color(0xFF9E9E9E), // Gray
        icon = Icons.Default.Circle,
        sortOrder = 3
    );
    
    companion object {
        fun fromDisplayName(name: String): TaskPriority {
            return values().find { it.displayName.equals(name, ignoreCase = true) } ?: NONE
        }
    }
}
