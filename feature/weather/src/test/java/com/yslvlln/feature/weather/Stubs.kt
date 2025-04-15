package com.yslvlln.feature.weather

import com.yslvlln.core.model.CurrentWeather

object Stubs {
    val CURRENT_WEATHER = CurrentWeather(
        cityName = "Test City",
        country = "TC",
        temperature = 298.15,
        feelsLike = 297.0,
        minTemperature = 295.0,
        maxTemperature = 300.0,
        humidity = 65,
        pressure = 1013,
        weatherDescription = "clear sky",
        weatherIconCode = "01d",
        windSpeed = 3.5,
        windDirection = 90,
        cloudiness = 5
    )
}