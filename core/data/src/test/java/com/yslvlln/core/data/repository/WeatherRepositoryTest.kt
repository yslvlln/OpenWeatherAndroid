package com.yslvlln.core.data.repository

import com.yslvlln.core.data.Stubs
import com.yslvlln.core.network.data.WeatherRemoteSource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals

class WeatherRepositoryTest {

    private val remoteSource: WeatherRemoteSource = mockk()
    private lateinit var repository: WeatherRepository

    @Before
    fun setUp() {
        repository = WeatherRepositoryImpl(remoteSource)
    }

    @Test
    fun `getCurrentWeather returns Success when remote source succeeds`() = runTest {
        val lat = 45.0
        val lng = 7.0

        val weatherResponse = Stubs.WEATHER_RESPONSE

        coEvery { remoteSource.getCurrentWeather(lat, lng) } returns Result.success(weatherResponse)

        val result = repository.getCurrentWeather(lat, lng)

        assertTrue(result.isSuccess)

        val currentWeather = result.getOrNull()
        assertNotNull(currentWeather)
        assertEquals("Province of Turin", currentWeather?.cityName)
        assertEquals(284.2, currentWeather?.temperature)
        assertEquals("moderate rain", currentWeather?.weatherDescription)
    }

    @Test
    fun `getCurrentWeather returns Failure when remote source fails`() = runTest {
        val lat = 45.0
        val lng = 7.0
        val exception = Exception("API Error")

        coEvery { remoteSource.getCurrentWeather(lat, lng) } returns Result.failure(exception)

        val result = repository.getCurrentWeather(lat, lng)

        assertTrue(result.isFailure)
        assertEquals("API Error", result.exceptionOrNull()?.message)
    }
}
