package com.yslvlln.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yslvlln.core.data.UserRepository
import com.yslvlln.feature.auth.state.SignUpUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class SignUpViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    private val _state = MutableStateFlow<SignUpUiState>(SignUpUiState.Idle)
    val state: StateFlow<SignUpUiState> = _state

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            _state.value = SignUpUiState.Loading
            val result = userRepository.signUp(email, password)
            _state.value = result.fold(
                onSuccess = {
                    SignUpUiState.Success(it)
                },
                onFailure = {
                    SignUpUiState.Error(it.message.orEmpty())
                },
            )
        }
    }
}