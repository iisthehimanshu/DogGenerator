package com.example.doggenerator.navigator

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object GenerateDogs : Screen("generateDogs")
    object RecentDogs : Screen("recentDogs")
}
