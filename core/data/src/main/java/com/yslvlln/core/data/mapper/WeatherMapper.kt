package com.yslvlln.core.data.mapper

import com.yslvlln.core.model.CurrentWeather
import com.yslvlln.core.network.model.WeatherResponse

fun WeatherResponse.toDomain(): CurrentWeather {
    return CurrentWeather(
        cityName = cityName,
        country = sys.countryCode,
        temperature = temperatureInfo.temperature,
        feelsLike = temperatureInfo.feelsLike,
        minTemperature = temperatureInfo.minTemperature,
        maxTemperature = temperatureInfo.maxTemperature,
        humidity = temperatureInfo.humidity,
        pressure = temperatureInfo.pressure,
        weatherDescription = weather.firstOrNull()?.description.orEmpty(),
        weatherIconCode = weather.firstOrNull()?.iconCode.orEmpty(),
        windSpeed = wind.speed,
        windDirection = wind.directionDegrees,
        cloudiness = clouds.cloudinessPercent
    )
}