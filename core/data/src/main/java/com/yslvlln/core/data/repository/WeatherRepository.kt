package com.yslvlln.core.data.repository

import com.yslvlln.core.model.CurrentWeather
import com.yslvlln.core.model.WeatherHistory

interface WeatherRepository {
    suspend fun getCurrentWeather(lat: Double, lng: Double): Result<CurrentWeather>
    suspend fun getWeatherHistory(): Result<List<WeatherHistory>>
}