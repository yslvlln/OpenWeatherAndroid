package com.yslvlln.feature.weather.screens.history

import com.yslvlln.core.model.WeatherHistory
import com.yslvlln.feature.weather.util.fahrenheitToCelsius
import com.yslvlln.feature.weather.util.toFormattedTime

fun WeatherHistory.toUiModel(): WeatherHistoryUiModel {
    return WeatherHistoryUiModel(
        cityName = cityName,
        country = country,
        temperature = "${temperature.toInt().fahrenheitToCelsius()}°C",
        weatherDescription = weatherDescription.replaceFirstChar { it.uppercaseChar() },
        sunriseTime = sunrise.toFormattedTime(),
        sunsetTime = sunset.toFormattedTime(),
        dateTime = timestamp.toFormattedTime("dd/MM/yyyy, hh:mm:ss a")
    )
}

data class WeatherHistoryUiModel(
    val cityName: String,
    val country: String,
    val temperature: String,
    val weatherDescription: String,
    val sunriseTime: String,
    val sunsetTime: String,
    val dateTime: String
)
