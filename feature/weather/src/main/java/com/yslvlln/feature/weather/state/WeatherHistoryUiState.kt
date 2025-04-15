package com.yslvlln.feature.weather.state

import com.yslvlln.core.model.WeatherHistory

sealed class WeatherHistoryUiState {
    data object Idle : WeatherHistoryUiState()
    data object Loading : WeatherHistoryUiState()
    data class Success(val data: List<WeatherHistory>?) : WeatherHistoryUiState()
    data class Error(val message: String) : WeatherHistoryUiState()
}