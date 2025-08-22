package dev.khaled.aroundegypt.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
}
