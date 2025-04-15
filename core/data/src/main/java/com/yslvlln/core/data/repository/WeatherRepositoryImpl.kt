package com.yslvlln.core.data.repository

import com.yslvlln.core.common.UNKNOWN_ERROR
import com.yslvlln.core.data.mapper.toDomain
import com.yslvlln.core.model.CurrentWeather
import com.yslvlln.core.network.data.WeatherRemoteSource
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val remoteSource: WeatherRemoteSource
) : WeatherRepository {

    override suspend fun getCurrentWeather(lat: Double, lng: Double): Result<CurrentWeather> {
        val result = remoteSource.getCurrentWeather(lat, lng)
        return if (result.isSuccess) {
            Result.success(result.getOrThrow().toDomain())
        } else {
            Result.failure(result.exceptionOrNull() ?: Exception(UNKNOWN_ERROR))
        }
    }
}
