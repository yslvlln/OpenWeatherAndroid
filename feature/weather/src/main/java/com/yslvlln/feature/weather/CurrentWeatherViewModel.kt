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
    private val weatherRepository: WeatherRepository,
    private val locationProvider: LocationProvider
) : ViewModel() {

    private val _state = MutableStateFlow<CurrentWeatherUiState>(CurrentWeatherUiState.RequestingPermission)
    val state: StateFlow<CurrentWeatherUiState> get() = _state

    fun onPermissionUpdate(isGranted: Boolean) {
        if (isGranted) {
            getCurrentWeather()
        } else {
            _state.value = CurrentWeatherUiState.PermissionDenied
        }
    }

    fun getCurrentWeather() {
        viewModelScope.launch {
            _state.value = CurrentWeatherUiState.Loading

            val location = locationProvider.getCurrentLocation()
            if (location == null) {
                _state.value = CurrentWeatherUiState.Error("Unable to retrieve location")
                return@launch
            }

            _state.value = CurrentWeatherUiState.Loading

            val result = weatherRepository.getCurrentWeather(location.latitude, location.longitude)
            _state.value = result.fold(
                onSuccess = { CurrentWeatherUiState.Success(it) },
                onFailure = { CurrentWeatherUiState.Error(it.message ?: "Unknown error") }
            )
        }
    }
}