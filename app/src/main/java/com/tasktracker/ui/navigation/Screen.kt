package com.tasktracker.ui.navigation

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object Stats : Screen("stats")
    object Settings : Screen("settings")
    object FocusMode : Screen("focus")
}
