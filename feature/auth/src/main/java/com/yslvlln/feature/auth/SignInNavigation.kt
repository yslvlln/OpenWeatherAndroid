package com.yslvlln.feature.auth

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object SignInNavigation

fun NavGraphBuilder.signInDestination(
    onSignIn: () -> Unit,
    onNavigateToSignUp: () -> Unit,
) {
    composable<SignInNavigation> {
        SignInScreen(
            onSignIn = onSignIn,
            onNavigateToSignUp = onNavigateToSignUp
        )
    }
}