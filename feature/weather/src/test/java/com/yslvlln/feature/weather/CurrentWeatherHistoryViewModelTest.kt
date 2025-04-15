package com.yslvlln.feature.weather

import android.location.Location
import com.yslvlln.core.data.repository.UserRepository
import com.yslvlln.core.data.repository.WeatherRepository
import com.yslvlln.core.testing.MainDispatcherRule
import com.yslvlln.feature.weather.screens.currentWeather.CurrentWeatherViewModel
import com.yslvlln.feature.weather.screens.currentWeather.LocationProvider
import com.yslvlln.feature.weather.state.CurrentWeatherUiState
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import kotlin.test.Test
import kotlin.test.assertIs

@OptIn(ExperimentalCoroutinesApi::class)
class CurrentWeatherHistoryViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val locationProvider: LocationProvider = mockk()
    private val userRepository: UserRepository = mockk()
    private val weatherRepository: WeatherRepository = mockk()
    private lateinit var viewModel: CurrentWeatherViewModel

    private val fakeWeather = Stubs.CURRENT_WEATHER

    @Before
    fun setup() {
        viewModel = CurrentWeatherViewModel(userRepository, weatherRepository, locationProvider)
    }

    @Test
    fun `onPermissionUpdate emits Loading then Success when permission is granted`() = runTest {
        // Given
        val location = mockk<Location>()
        every { location.latitude } returns 1.0
        every { location.longitude } returns 1.0
        coEvery { locationProvider.getCurrentLocation() } returns location
        coEvery { weatherRepository.getCurrentWeather(1.0, 1.0) } returns Result.success(fakeWeather)

        // When
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.state.collect()
        }

        viewModel.onPermissionUpdate(true)
        advanceUntilIdle()

        // Then
        val currentState = viewModel.state.value
        assertIs<CurrentWeatherUiState.Success>(currentState)
        assertEquals(fakeWeather, currentState.data)
    }

    @Test
    fun `onPermissionUpdate emits PermissionDenied when permission is denied`() = runTest {
        // Given — nothing to mock

        // When
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.state.collect()
        }

        viewModel.onPermissionUpdate(false)
        advanceUntilIdle()

        // Then
        val currentState = viewModel.state.value
        assertEquals(CurrentWeatherUiState.PermissionDenied, currentState)
    }

    @Test
    fun `getCurrentWeather emits Error when location is null`() = runTest {
        // Given
        coEvery { locationProvider.getCurrentLocation() } returns null

        // When
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.state.collect()
        }

        viewModel.getCurrentWeather()
        advanceUntilIdle()

        // Then
        val currentState = viewModel.state.value
        assertIs<CurrentWeatherUiState.Error>(currentState)
        assertEquals("Unable to retrieve location", currentState.message)
    }

    @Test
    fun `getCurrentWeather emits Error when repository fails`() = runTest {
        // Given
        val location = mockk<Location>()
        every { location.latitude } returns 1.0
        every { location.longitude } returns 1.0
        coEvery { locationProvider.getCurrentLocation() } returns location
        coEvery { weatherRepository.getCurrentWeather(1.0, 1.0) } returns Result.failure(Exception("API failed"))

        // When
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.state.collect()
        }

        viewModel.getCurrentWeather()
        advanceUntilIdle()

        // Then
        val currentState = viewModel.state.value
        assertIs<CurrentWeatherUiState.Error>(currentState)
        assertEquals("API failed", currentState.message)
    }
}
