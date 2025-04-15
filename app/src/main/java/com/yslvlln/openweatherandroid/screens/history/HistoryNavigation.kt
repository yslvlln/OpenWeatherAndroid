package com.yslvlln.openweatherandroid.screens.history

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object WeatherHistoryRoute

fun NavGraphBuilder.historyDestination() {
    composable<WeatherHistoryRoute> {
        HistoryScreen()
    }
}