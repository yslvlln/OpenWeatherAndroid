package com.yslvlln.feature.auth.state

import com.yslvlln.core.data.model.User

sealed class SignInUiState {
    data object Idle: SignInUiState()
    data object Loading: SignInUiState()
    data class Success(val user: User): SignInUiState()
    data class Error(val message: String): SignInUiState()
}