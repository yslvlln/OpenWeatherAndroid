package com.yslvlln.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    @SerialName("coord") val coordinate: Coordinate,
    @SerialName("weather") val weather: List<Weather>,
    @SerialName("base") val base: String,
    @SerialName("main") val temperatureInfo: TemperatureInfo,
    @SerialName("visibility") val visibility: Int,
    @SerialName("wind") val wind: Wind,
    @SerialName("rain") val rain: Rain? = null,
    @SerialName("clouds") val clouds: Clouds,
    @SerialName("dt") val timestamp: Long,
    @SerialName("sys") val sys: Sys,
    @SerialName("timezone") val timezoneOffset: Int,
    @SerialName("id") val cityId: Long,
    @SerialName("name") val cityName: String,
    @SerialName("cod") val responseCode: Int
)

@Serializable
data class Coordinate(
    @SerialName("lon") val longitude: Double,
    @SerialName("lat") val latitude: Double
)

@Serializable
data class Weather(
    @SerialName("id") val id: Int,
    @SerialName("main") val main: String,
    @SerialName("description") val description: String,
    @SerialName("icon") val iconCode: String
)

@Serializable
data class TemperatureInfo(
    @SerialName("temp") val temperature: Double,
    @SerialName("feels_like") val feelsLike: Double,
    @SerialName("temp_min") val minTemperature: Double,
    @SerialName("temp_max") val maxTemperature: Double,
    @SerialName("pressure") val pressure: Int,
    @SerialName("humidity") val humidity: Int,
    @SerialName("sea_level") val seaLevel: Int? = null,
    @SerialName("grnd_level") val groundLevel: Int? = null
)

@Serializable
data class Wind(
    @SerialName("speed") val speed: Double,
    @SerialName("deg") val directionDegrees: Int,
    @SerialName("gust") val gustSpeed: Double? = null
)

@Serializable
data class Rain(
    @SerialName("1h") val oneHourVolume: Double
)

@Serializable
data class Clouds(
    @SerialName("all") val cloudinessPercent: Int
)

@Serializable
data class Sys(
    @SerialName("type") val type: Int? = null,
    @SerialName("id") val systemId: Int? = null,
    @SerialName("country") val countryCode: String,
    @SerialName("sunrise") val sunriseTime: Long,
    @SerialName("sunset") val sunsetTime: Long
)
