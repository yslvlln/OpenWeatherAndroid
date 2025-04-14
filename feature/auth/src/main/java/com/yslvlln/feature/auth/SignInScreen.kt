package com.yslvlln.feature.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        when (state) {
            is SignInUiState.Success -> {
                onSignIn.invoke()
            }

            is SignInUiState.Error -> {
                // TODO
            }

            SignInUiState.Idle -> {
                SignInForm(
                    email = email,
                    password = password,
                    onEmailChange = { viewModel.onEmailChange(it) },
                    onPasswordChange = { viewModel.onPasswordChange(it) },
                    onSignInClick = { viewModel.signIn(email, password) },
                    onNavigateToSignUp = { onNavigateToSignUp.invoke() }
                )
            }

            SignInUiState.Loading -> {
                // TODO
            }
        }
    }

}

@Composable
internal fun SignInForm(
    email: String = "",
    password: String = "",
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignInClick: () -> Unit,
    onNavigateToSignUp: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
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

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { onSignInClick.invoke() },
                modifier = Modifier.fillMaxWidth(),
                enabled = email.isNotEmpty() && password.isNotEmpty()
            ) {
                Text("Login")
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