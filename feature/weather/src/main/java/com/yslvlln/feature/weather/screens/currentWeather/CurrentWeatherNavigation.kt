package com.yslvlln.feature.weather.screens.currentWeather

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object CurrentWeatherRoute

fun NavGraphBuilder.currentWeatherDestination() {
    composable<CurrentWeatherRoute> {
        CurrentWeatherScreen()
    }
}