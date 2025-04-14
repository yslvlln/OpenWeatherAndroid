package com.yslvlln.openweatherandroid.screens

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object MainRoute

fun NavGraphBuilder.mainDestination() {
    composable<MainRoute> {
        MainScreen()
    }
}
