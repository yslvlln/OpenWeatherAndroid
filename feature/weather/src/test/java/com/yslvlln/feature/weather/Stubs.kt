package com.yslvlln.feature.weather

import com.yslvlln.core.model.CurrentWeather

object Stubs {
    val CURRENT_WEATHER = CurrentWeather(
        cityName = "Test City",
        country = "TC",
        temperature = 298.15,
        weatherDescription = "clear sky",
        sunset = 1726636384,
        sunrise = 1726636384
    )
}