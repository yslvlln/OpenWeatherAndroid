package com.yslvlln.feature.weather.state

import com.yslvlln.core.model.CurrentWeather

sealed class CurrentWeatherUiState {
    data object RequestingPermission : CurrentWeatherUiState()
    data object Loading : CurrentWeatherUiState()
    data object PermissionDenied : CurrentWeatherUiState()
    data class Success(val data: CurrentWeather?) : CurrentWeatherUiState()
    data class Error(val message: String) : CurrentWeatherUiState()
}