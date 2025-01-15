package com.example.doggenerator.navigator

import androidx.navigation.NavController

class NavigatorAdapter(private val navController: NavController) {

    fun navigateTo(screen: Screen) {
        navController.navigate(screen.route)
    }

    fun navigateBack() {
        navController.popBackStack()
    }
}
