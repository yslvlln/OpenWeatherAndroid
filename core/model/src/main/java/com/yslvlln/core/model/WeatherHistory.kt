package com.yslvlln.core.model

data class WeatherHistory(
    val cityName: String,
    val country: String,
    val temperature: Double,
    val weatherDescription: String,
    val sunrise: Long,
    val sunset: Long,
    val timestamp: Long
)