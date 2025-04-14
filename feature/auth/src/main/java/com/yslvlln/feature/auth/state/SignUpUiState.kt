package com.yslvlln.feature.auth.state

import com.yslvlln.core.data.model.User

sealed class SignUpUiState {
    data object Idle: SignUpUiState()
    data object Loading: SignUpUiState()
    data class Success(val user: User): SignUpUiState()
    data class Error(val message: String): SignUpUiState()
}