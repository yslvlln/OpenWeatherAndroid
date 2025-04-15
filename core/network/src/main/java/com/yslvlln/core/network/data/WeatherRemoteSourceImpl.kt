package com.yslvlln.core.network.data

import com.yslvlln.core.network.BuildConfig
import com.yslvlln.core.network.WeatherApiService
import com.yslvlln.core.network.model.WeatherResponse
import javax.inject.Inject

class WeatherRemoteSourceImpl @Inject constructor(
    private val weatherApiService: WeatherApiService
) : WeatherRemoteSource {

    override suspend fun getCurrentWeather(lat: Double, lng: Double): Result<WeatherResponse> {
        return try {
            val response = weatherApiService
                .getCurrentWeather(
                    latitude = lat,
                    longitude = lng,
                    apiKey = BuildConfig.API_KEY
                )
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}