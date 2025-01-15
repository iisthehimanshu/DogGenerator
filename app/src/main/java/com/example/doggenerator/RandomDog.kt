package com.example.doggenerator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.doggenerator.navigator.NavigatorAdapter
import com.example.doggenerator.screens.GenerateDogsScreen
import com.example.doggenerator.screens.HomeScreen
import com.example.doggenerator.screens.RecentDogsScreen

@Composable
fun RandomDogApp() {
    val navController = rememberNavController()
    val navigatorAdapter = remember { NavigatorAdapter(navController) }

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onFirstButtonClick = { navController.navigate("generateDogs") },
                onSecondButtonClick = { navController.navigate("recentDogs") },
                navigator = navigatorAdapter
            )
        }
        composable("generateDogs") {
            val context = LocalContext.current
            GenerateDogsScreen(context = context,navigator = navigatorAdapter)
        }
        composable("recentDogs") {
            val context = LocalContext.current
            RecentDogsScreen(context = context,navigator = navigatorAdapter)
        }
    }
}
