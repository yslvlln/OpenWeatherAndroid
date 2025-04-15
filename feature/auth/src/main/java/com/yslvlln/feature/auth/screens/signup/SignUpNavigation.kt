package com.yslvlln.feature.auth.screens.signup

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object SignUpRoute

fun NavGraphBuilder.signUpDestination(
    onSignUp: () -> Unit,
    onNavigateUp: () -> Unit,
) {
    composable<SignUpRoute> {
        SignUpScreen(
            onSignUp = onSignUp,
            onNavigateUp = onNavigateUp
        )
    }
}