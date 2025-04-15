package com.yslvlln.feature.weather

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