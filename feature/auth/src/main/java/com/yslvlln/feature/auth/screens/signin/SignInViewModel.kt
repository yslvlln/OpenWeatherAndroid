package com.yslvlln.feature.auth.screens.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yslvlln.core.data.UserRepository
import com.yslvlln.feature.auth.state.SignInUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _state = MutableStateFlow<SignInUiState>(SignInUiState.Idle)
    val state: StateFlow<SignInUiState> = _state

    fun onEmailChange(email: String) {
        _email.value = email
    }

    fun onPasswordChange(password: String) {
        _password.value = password
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _state.value = SignInUiState.Loading
            val result = userRepository.signIn(email, password)
            _state.value = result.fold(
                onSuccess = {
                    SignInUiState.Success(it)
                },
                onFailure = {
                    SignInUiState.Error(it.message.orEmpty())
                },
            )
        }
    }

}