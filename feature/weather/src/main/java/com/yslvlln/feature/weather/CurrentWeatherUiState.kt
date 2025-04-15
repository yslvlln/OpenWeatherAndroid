package com.yslvlln.feature.weather

import com.yslvlln.core.model.CurrentWeather

sealed class CurrentWeatherUiState {
    data object Idle : CurrentWeatherUiState()
    data object Loading : CurrentWeatherUiState()
    data class Success(val weather: CurrentWeather?) : CurrentWeatherUiState()
    data class Error(val errorMessage: String?) : CurrentWeatherUiState()
}