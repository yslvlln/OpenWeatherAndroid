package com.yslvlln.feature.weather.screens.history

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object WeatherHistoryRoute

fun NavGraphBuilder.weatherHistoryDestination() {
    composable<WeatherHistoryRoute> {
        WeatherHistoryScreen()
    }
}