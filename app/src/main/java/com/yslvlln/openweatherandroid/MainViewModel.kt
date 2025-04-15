package com.yslvlln.openweatherandroid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yslvlln.core.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class InitialNavigation {
    LANDING,
    HOME
}

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _state = MutableStateFlow<InitialNavigation?>(null)
    val state: StateFlow<InitialNavigation?> = _state

    fun getUser() {
        viewModelScope.launch {
            val firebaseUser = userRepository.getUser()
            if (firebaseUser != null) {
                _state.value = InitialNavigation.HOME
            } else {
                _state.value = InitialNavigation.LANDING
            }
        }
    }
}