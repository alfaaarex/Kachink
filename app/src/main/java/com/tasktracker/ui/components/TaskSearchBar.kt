package com.tasktracker.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tasktracker.data.model.TaskLabel
import com.tasktracker.data.model.TaskPriority
import com.tasktracker.ui.theme.ExpressiveShapes
import com.tasktracker.ui.theme.rememberHapticFeedback

data class TaskFilters(
    val searchQuery: String = "",
    val selectedLabels: Set<TaskLabel> = emptySet(),
    val selectedPriorities: Set<TaskPriority> = emptySet(),
    val showCompleted: Boolean = true,
    val showScheduled: Boolean = true,
    val showUnscheduled: Boolean = true
)

/**
 * Search bar with filter chips for task filtering
 * Material 3 Expressive: Quick, intuitive, visual
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    filters: TaskFilters,
    onFiltersChange: (TaskFilters) -> Unit,
    modifier: Modifier = Modifier
) {
    val haptic = rememberHapticFeedback()
    var isExpanded by remember { mutableStateOf(false) }
    
    Column(modifier = modifier) {
        // Search field
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search tasks...") },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Search")
            },
            trailingIcon = {
                Row {
                    if (query.isNotEmpty()) {
                        IconButton(onClick = { onQueryChange("") }) {
                            Icon(Icons.Default.Clear, "Clear search")
                        }
                    }
                    IconButton(onClick = { 
                        isExpanded = !isExpanded
                        haptic.lightTap()
                    }) {
                        Icon(
                            imageVector = if (isExpanded) Icons.Default.FilterListOff else Icons.Default.FilterList,
                            contentDescription = "Filters"
                        )
                    }
                }
            },
            shape = ExpressiveShapes.labelChip,
            singleLine = true
        )
        
        // Filter chips
        AnimatedVisibility(
            visible = isExpanded,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut()
        ) {
            Column(
                modifier = Modifier.padding(top = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Status filters
                Text(
                    text = "Status",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        FilterChip(
                            selected = filters.showCompleted,
                            onClick = {
                                onFiltersChange(filters.copy(showCompleted = !filters.showCompleted))
                                haptic.selectionChange()
                            },
                            label = { Text("Completed") },
                            leadingIcon = {
                                Icon(Icons.Default.CheckCircle, null, modifier = Modifier.size(18.dp))
                            }
                        )
                    }
                    item {
                        FilterChip(
                            selected = filters.showScheduled,
                            onClick = {
                                onFiltersChange(filters.copy(showScheduled = !filters.showScheduled))
                                haptic.selectionChange()
                            },
                            label = { Text("Scheduled") },
                            leadingIcon = {
                                Icon(Icons.Default.Schedule, null, modifier = Modifier.size(18.dp))
                            }
                        )
                    }
                    item {
                        FilterChip(
                            selected = filters.showUnscheduled,
                            onClick = {
                                onFiltersChange(filters.copy(showUnscheduled = !filters.showUnscheduled))
                                haptic.selectionChange()
                            },
                            label = { Text("Unscheduled") },
                            leadingIcon = {
                                Icon(Icons.Default.Inbox, null, modifier = Modifier.size(18.dp))
                            }
                        )
                    }
                }
                
                // Priority filters
                Text(
                    text = "Priority",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(TaskPriority.values().filter { it != TaskPriority.NONE }) { priority ->
                        FilterChip(
                            selected = priority in filters.selectedPriorities,
                            onClick = {
                                val newPriorities = if (priority in filters.selectedPriorities) {
                                    filters.selectedPriorities - priority
                                } else {
                                    filters.selectedPriorities + priority
                                }
                                onFiltersChange(filters.copy(selectedPriorities = newPriorities))
                                haptic.selectionChange()
                            },
                            label = { Text(priority.displayName) },
                            leadingIcon = {
                                Icon(
                                    imageVector = priority.icon,
                                    contentDescription = null,
                                    tint = priority.color,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        )
                    }
                }
                
                // Category filters
                Text(
                    text = "Category",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(TaskLabel.values()) { label ->
                        FilterChip(
                            selected = label in filters.selectedLabels,
                            onClick = {
                                val newLabels = if (label in filters.selectedLabels) {
                                    filters.selectedLabels - label
                                } else {
                                    filters.selectedLabels + label
                                }
                                onFiltersChange(filters.copy(selectedLabels = newLabels))
                                haptic.selectionChange()
                            },
                            label = { Text(label.displayName) }
                        )
                    }
                }
            }
        }
    }
}
