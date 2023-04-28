package com.meta.littlelemon.util

import androidx.navigation.NavController
import com.meta.littlelemon.screens.Destination

fun NavController.navigate(destination: Destination, clearBackStack: Boolean = false) {
    val navController = this
    navigate(destination.name) out@{
        if (!clearBackStack) return@out
        popUpTo(navController.graph.id) {
            inclusive = true
        }
    }
}