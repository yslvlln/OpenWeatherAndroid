package com.yslvlln.feature.auth.screens.signin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yslvlln.feature.auth.state.SignInUiState

@Composable
fun SignInScreen(
    onSignIn: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val email by viewModel.email.collectAsStateWithLifecycle()
    val password by viewModel.password.collectAsStateWithLifecycle()

    LaunchedEffect(state) {
        if (state is SignInUiState.Success) {
            onSignIn.invoke()
        }
    }

    SignInForm(
        email = email,
        password = password,
        onEmailChange = { viewModel.onEmailChange(it) },
        onPasswordChange = { viewModel.onPasswordChange(it) },
        onSignInClick = { viewModel.signIn(email, password) },
        onNavigateToSignUp = { onNavigateToSignUp.invoke() },
        isLoading = state is SignInUiState.Loading,
        errorMessage = (state as? SignInUiState.Error)?.message
    )
}

@Composable
internal fun SignInForm(
    email: String = "",
    password: String = "",
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignInClick: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    isLoading: Boolean = false,
    errorMessage: String? = null
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Sign in to your \naccount",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Please enter your email and password to continue.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = email,
                onValueChange = { onEmailChange.invoke(it) },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = password,
                onValueChange = { onPasswordChange.invoke(it) },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )

            if (!errorMessage.isNullOrEmpty()) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { onSignInClick.invoke() },
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
                    Text("Login")
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextButton(
                onClick = onNavigateToSignUp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(
                    buildAnnotatedString {
                        append("Don't have an account? ")
                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                            append("Sign Up")
                        }
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface // default text color
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
internal fun SignInFormPreview() {
    SignInForm(
        onEmailChange = {},
        onPasswordChange = {},
        onSignInClick = {},
        onNavigateToSignUp = {}
    )
}