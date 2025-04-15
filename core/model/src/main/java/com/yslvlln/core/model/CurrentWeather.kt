package com.yslvlln.core.model

data class CurrentWeather(
    val cityName: String,
    val country: String,
    val temperature: Double,
    val feelsLike: Double,
    val minTemperature: Double,
    val maxTemperature: Double,
    val humidity: Int,
    val pressure: Int,
    val weatherDescription: String,
    val weatherIconCode: String,
    val windSpeed: Double,
    val windDirection: Int,
    val cloudiness: Int
)
