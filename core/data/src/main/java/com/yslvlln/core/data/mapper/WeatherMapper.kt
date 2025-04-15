package com.yslvlln.core.data.mapper

import com.yslvlln.core.database.model.WeatherEntity
import com.yslvlln.core.model.CurrentWeather
import com.yslvlln.core.network.model.WeatherResponse

fun WeatherResponse.toDomain(): CurrentWeather {
    return CurrentWeather(
        cityName = cityName,
        country = sys.countryCode,
        temperature = temperatureInfo.temperature,
        weatherDescription = weather.firstOrNull()?.description.orEmpty(),
        sunset = sys.sunsetTime,
        sunrise = sys.sunriseTime
    )
}

fun WeatherResponse.toEntity(): WeatherEntity {
    return WeatherEntity(
        cityName = cityName,
        country = sys.countryCode,
        temperature = temperatureInfo.temperature,
        weatherDescription = weather.firstOrNull()?.description.orEmpty(),
        sunset = sys.sunsetTime,
        sunrise = sys.sunriseTime,
        timestamp = timestamp
    )
}