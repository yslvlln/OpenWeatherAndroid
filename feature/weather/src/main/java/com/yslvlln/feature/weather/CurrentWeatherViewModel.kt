package com.yslvlln.feature.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yslvlln.core.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _state = MutableStateFlow<CurrentWeatherUiState>(CurrentWeatherUiState.Idle)
    val state: StateFlow<CurrentWeatherUiState> get() = _state

    fun getCurrentWeather(lat: Double, lng: Double) {
        _state.value = CurrentWeatherUiState.Loading
        viewModelScope.launch {
            val result = weatherRepository.getCurrentWeather(lat, lng)
            _state.value = when {
                result.isSuccess -> CurrentWeatherUiState.Success(result.getOrNull())
                result.isFailure -> CurrentWeatherUiState.Error(result.exceptionOrNull()?.message)
                else -> CurrentWeatherUiState.Idle
            }
        }
    }
}