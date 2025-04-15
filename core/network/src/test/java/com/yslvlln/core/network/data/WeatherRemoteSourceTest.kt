package com.yslvlln.core.network.data

import com.yslvlln.core.network.Stubs
import com.yslvlln.core.network.WeatherApiService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import retrofit2.Response
import kotlin.test.Test


@ExperimentalCoroutinesApi
class WeatherRemoteSourceTest {

    private val apiService: WeatherApiService = mockk()
    private lateinit var remoteSource: WeatherRemoteSource

    @Before
    fun setUp() {
        remoteSource = WeatherRemoteSourceImpl(apiService)
    }

    @Test
    fun `getCurrentWeather returns Success when API call succeeds`() = runTest {
        val lat = 45.0
        val lng = 7.0
        val mockResponse = Stubs.WEATHER_RESPONSE

        coEvery { apiService.getCurrentWeather(lat, lng, any()) } returns Response.success(mockResponse)

        val result = remoteSource.getCurrentWeather(lat, lng)

        assertTrue(result.isSuccess)
        assertEquals(mockResponse, result.getOrNull())
    }

    @Test
    fun `getCurrentWeather returns Failure when API call throws exception`() = runTest {
        val lat = 0.0
        val lng = 0.0
        val exception = RuntimeException("Network error")

        coEvery { apiService.getCurrentWeather(lat, lng, any()) } throws exception

        val result = remoteSource.getCurrentWeather(lat, lng)

        assertTrue(result.isFailure)
        assertEquals(exception.message, result.exceptionOrNull()?.message)
    }
}
