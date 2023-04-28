package com.meta.littlelemon.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.meta.littlelemon.Onboarding

@Composable
fun Navigation(isLoggedIn: Boolean) {
    val navController = rememberNavController()
    val startDestination = if (isLoggedIn) Destination.Home else Destination.Onboarding
    NavHost(navController = navController, startDestination = startDestination.name) {
        composable(Destination.Onboarding.name) {
            Onboarding(navController)
        }
        composable(Destination.Home.name) {
            Home(navController)
        }
        composable(Destination.Profile.name) {
            Profile(navController)
        }
    }
}