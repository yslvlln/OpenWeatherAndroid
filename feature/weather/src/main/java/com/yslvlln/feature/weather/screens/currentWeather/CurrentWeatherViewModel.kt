package com.yslvlln.feature.weather.screens.currentWeather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yslvlln.core.data.repository.UserRepository
import com.yslvlln.core.data.repository.WeatherRepository
import com.yslvlln.feature.weather.state.CurrentWeatherUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val weatherRepository: WeatherRepository,
    private val locationProvider: LocationProvider
) : ViewModel() {

    private val _state = MutableStateFlow<CurrentWeatherUiState>(CurrentWeatherUiState.Idle)
    val state: StateFlow<CurrentWeatherUiState> get() = _state

    fun getUser() {
        viewModelScope.launch {
            val firebaseUser = userRepository.getUser()
            if (firebaseUser != null) {
                _state.value = CurrentWeatherUiState.RequestingPermission
            }
        }
    }

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