package com.yslvlln.feature.weather.screens.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yslvlln.core.data.repository.WeatherRepository
import com.yslvlln.feature.weather.state.WeatherHistoryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherHistoryViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _state = MutableStateFlow<WeatherHistoryUiState>(WeatherHistoryUiState.Idle)
    val state: StateFlow<WeatherHistoryUiState> = _state

    fun getWeatherHistory() {
        viewModelScope.launch {
            _state.value = WeatherHistoryUiState.Loading
            val result = weatherRepository.getWeatherHistory()
            _state.value = result.fold(
                onSuccess = { WeatherHistoryUiState.Success(it) },
                onFailure = { WeatherHistoryUiState.Error(it.message ?: "Unknown error") }
            )
        }
    }
}