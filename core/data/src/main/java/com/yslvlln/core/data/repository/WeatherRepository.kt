package com.yslvlln.core.data.repository

import com.yslvlln.core.model.CurrentWeather

interface WeatherRepository {
    suspend fun getCurrentWeather(lat: Double, lng: Double): Result<CurrentWeather>
}