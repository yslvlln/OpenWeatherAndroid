package com.yslvlln.feature.weather

import com.yslvlln.core.data.repository.WeatherRepository
import com.yslvlln.core.testing.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CurrentWeatherViewModelTest {

    private val weatherRepository: WeatherRepository = mockk()
    private val locationProvider: LocationProvider = mockk()
    private lateinit var viewModel: CurrentWeatherViewModel

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        viewModel = CurrentWeatherViewModel(weatherRepository, locationProvider)
    }

    @Test
    fun `getCurrentWeather returns Success when repository succeeds`() = runTest {
        // Given
        val lat = 45.0
        val lng = 7.0
        val weatherResponse = Stubs.CURRENT_WEATHER

        coEvery { weatherRepository.getCurrentWeather(lat, lng) } returns Result.success(weatherResponse)

        // When
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.state.collect()
        }

        viewModel.getCurrentWeather()
        advanceUntilIdle()

        // Then
        assertTrue(viewModel.state.value is CurrentWeatherUiState.Success)
        val successState = viewModel.state.value as CurrentWeatherUiState.Success
        assertNotNull(successState.data)
        assertEquals(Stubs.CURRENT_WEATHER.cityName, successState.data?.cityName)
        assertEquals(Stubs.CURRENT_WEATHER.temperature, successState.data?.temperature)
        assertEquals(Stubs.CURRENT_WEATHER.weatherDescription, successState.data?.weatherDescription)
    }

    @Test
    fun `getCurrentWeather returns Error when repository fails`() = runTest {
        // Given
        val lat = 45.0
        val lng = 7.0
        val errorMessage = "API Error"

        coEvery { weatherRepository.getCurrentWeather(lat, lng) } returns Result.failure(Exception(errorMessage))

        // When
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.state.collect()
        }

        viewModel.getCurrentWeather()
        advanceUntilIdle()

        // Then
        assertTrue(viewModel.state.value is CurrentWeatherUiState.Error)
        val errorState = viewModel.state.value as CurrentWeatherUiState.Error
        assertEquals(errorMessage, errorState.message)
    }

    @Test
    fun `getCurrentWeather returns Loading and then Success`() = runTest {
        // Given
        val lat = 45.0
        val lng = 7.0

        coEvery { weatherRepository.getCurrentWeather(lat, lng) } returns Result.success(Stubs.CURRENT_WEATHER)

        // When
        val collectedStates = mutableListOf<CurrentWeatherUiState>()

        val job = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.state.toList(collectedStates)
        }

        // Then
        viewModel.getCurrentWeather()
        advanceUntilIdle()

        assertTrue(collectedStates.first() is CurrentWeatherUiState.RequestingPermission)
        assertTrue(collectedStates[1] is CurrentWeatherUiState.Loading)
        assertTrue(collectedStates.last() is CurrentWeatherUiState.Success)

        job.cancel()
    }
}