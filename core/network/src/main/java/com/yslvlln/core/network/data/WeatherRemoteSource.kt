package com.yslvlln.core.network.data

import com.yslvlln.core.network.model.WeatherResponse

interface WeatherRemoteSource {
    suspend fun getCurrentWeather(lat: Double, lng: Double): Result<WeatherResponse>
}