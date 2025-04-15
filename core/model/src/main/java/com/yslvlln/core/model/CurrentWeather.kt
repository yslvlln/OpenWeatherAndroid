package com.yslvlln.core.model

data class CurrentWeather(
    val cityName: String,
    val country: String,
    val temperature: Double,
    val weatherDescription: String,
    val sunrise: Long,
    val sunset: Long
)