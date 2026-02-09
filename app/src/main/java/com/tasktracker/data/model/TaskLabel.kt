package com.tasktracker.data.model

import androidx.compose.ui.graphics.Color

enum class TaskLabel(
    val displayName: String,
    val lightColor: Color,
    val darkColor: Color,
    val icon: String
) {
    EDUCATION(
        displayName = "Education",
        lightColor = Color(0xFF6750A4), // Purple
        darkColor = Color(0xFFD0BCFF),
        icon = "school"
    ),
    PERSONAL(
        displayName = "Personal",
        lightColor = Color(0xFF006A6A), // Teal
        darkColor = Color(0xFF4DD0E1),
        icon = "person"
    ),
    ACTIVITY(
        displayName = "Activity",
        lightColor = Color(0xFF984061), // Pink
        darkColor = Color(0xFFFFB1C8),
        icon = "fitness_center"
    ),
    WORK(
        displayName = "Work",
        lightColor = Color(0xFF006874), // Blue
        darkColor = Color(0xFF95F0FF),
        icon = "work"
    ),
    HEALTH(
        displayName = "Health",
        lightColor = Color(0xFF8B5000), // Orange
        darkColor = Color(0xFFFFB951),
        icon = "favorite"
    ),
    SOCIAL(
        displayName = "Social",
        lightColor = Color(0xFF904A00), // Deep Orange
        darkColor = Color(0xFFFFB77C),
        icon = "groups"
    ),
    CUSTOM(
        displayName = "Custom",
        lightColor = Color(0xFF6E6E6E), // Gray
        darkColor = Color(0xFFC4C4C4),
        icon = "label"
    );
    
    companion object {
        fun fromDisplayName(name: String): TaskLabel {
            return values().find { it.displayName.equals(name, ignoreCase = true) } ?: CUSTOM
        }
    }
}
