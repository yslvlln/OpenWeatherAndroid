package com.yslvlln.feature.auth.screens.signup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yslvlln.core.data.repository.UserRepository
import com.yslvlln.feature.auth.state.SignUpUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _state = MutableStateFlow<SignUpUiState>(SignUpUiState.Idle)
    val state: StateFlow<SignUpUiState> = _state

    fun onEmailChange(email: String) {
        _email.value = email
    }

    fun onPasswordChange(password: String) {
        _password.value = password
    }

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            _state.value = SignUpUiState.Loading
            val result = userRepository.signUp(email, password)
            _state.value = result.fold(
                onSuccess = {
                    SignUpUiState.Success(it)
                },
                onFailure = {
                    Log.e(this::class.java.simpleName, it.message.orEmpty())
                    SignUpUiState.Error(it.message.orEmpty())
                },
            )
        }
    }
}