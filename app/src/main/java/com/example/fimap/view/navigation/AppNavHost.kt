package com.example.fimap.view.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fimap.view.login.LoginScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable(route = "login") { LoginScreen(navController = navController) }

//        composable(route = "dashboard") { DashboardScreen(navController = navController) }
//
//        composable(route = "config") { ConfiguracaoScreen(navController = navController) }
    }
}