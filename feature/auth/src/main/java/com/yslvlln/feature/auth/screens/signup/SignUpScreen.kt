package com.yslvlln.feature.auth.screens.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yslvlln.feature.auth.state.SignUpUiState

@Composable
fun SignUpScreen(
    onSignUp: () -> Unit,
    onNavigateUp: () -> Unit,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val email by viewModel.email.collectAsStateWithLifecycle()
    val password by viewModel.password.collectAsStateWithLifecycle()

    LaunchedEffect(state) {
        if (state is SignUpUiState.Success) {
            onSignUp.invoke()
        }
    }

    SignUpForm(
        email = email,
        password = password,
        onEmailChange = { viewModel.onEmailChange(it) },
        onPasswordChange = { viewModel.onPasswordChange(it) },
        onSignUpClick = { viewModel.signUp(email, password) },
        onBackClick = { onNavigateUp.invoke() },
        isLoading = state is SignUpUiState.Loading,
        errorMessage = (state as? SignUpUiState.Error)?.message
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SignUpForm(
    email: String = "",
    password: String = "",
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignUpClick: () -> Unit,
    onBackClick: () -> Unit,
    isLoading: Boolean = false,
    errorMessage: String? = null
) {
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(top = 12.dp),
                title = {
                    Text(
                        text = "Create Account",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Text(
                    text = "Sign up to get started",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = onEmailChange,
                    label = { Text("Email") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = onPasswordChange,
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                if (!errorMessage.isNullOrEmpty()) {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Button(
                    onClick = onSignUpClick,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading && (email.isNotEmpty() && password.isNotEmpty())
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = MaterialTheme.colorScheme.onPrimary,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text("Sign Up")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
internal fun SignUpFormPreview() {
    SignUpForm(
        onEmailChange = {},
        onSignUpClick = {},
        onBackClick = {},
        onPasswordChange = {}
    )
}
