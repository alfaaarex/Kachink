package com.tasktracker.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

// Material 3 Expressive Shapes with playful, varied corner radii
val Shapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(32.dp)
)

// Custom expressive shapes for specific components
object ExpressiveShapes {
    val taskCard = RoundedCornerShape(20.dp)
    val taskCardExpanded = RoundedCornerShape(24.dp)
    val labelChip = RoundedCornerShape(16.dp)
    val dialog = RoundedCornerShape(28.dp)
    val bottomSheet = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
    val fab = RoundedCornerShape(16.dp)
    val timelineMarker = RoundedCornerShape(12.dp)
}
