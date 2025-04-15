package com.yslvlln.core.data

import com.yslvlln.core.network.model.Clouds
import com.yslvlln.core.network.model.Coordinate
import com.yslvlln.core.network.model.Rain
import com.yslvlln.core.network.model.Sys
import com.yslvlln.core.network.model.TemperatureInfo
import com.yslvlln.core.network.model.Weather
import com.yslvlln.core.network.model.WeatherResponse
import com.yslvlln.core.network.model.Wind

object Stubs {

    val WEATHER_RESPONSE = WeatherResponse(
        coordinate = Coordinate(latitude = 7.367, longitude = 45.133),
        weather = listOf(
            Weather(
                id = 501,
                main = "Rain",
                description = "moderate rain",
                iconCode = "10d"
            )
        ),
        base = "stations",
        temperatureInfo = TemperatureInfo(
            temperature = 284.2,
            feelsLike = 282.93,
            minTemperature = 283.06,
            maxTemperature = 286.82,
            pressure = 1021,
            humidity = 60,
            seaLevel = 1021,
            groundLevel = 910
        ),
        visibility = 10000,
        wind = Wind(
            speed = 4.09,
            directionDegrees = 121,
            gustSpeed = 3.47
        ),
        rain = Rain(
            oneHourVolume = 2.73
        ),
        clouds = Clouds(
            cloudinessPercent = 83
        ),
        timestamp = 1726660758,
        sys = Sys(
            type = 1,
            systemId = 6736,
            countryCode = "IT",
            sunriseTime = 1726636384,
            sunsetTime = 1726680975
        ),
        timezoneOffset = 7200,
        cityId = 3165523,
        cityName = "Province of Turin",
        responseCode = 200
    )
}