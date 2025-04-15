package com.yslvlln.feature.auth.screens.signin

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object SignInRoute

fun NavGraphBuilder.signInDestination(
    onSignIn: () -> Unit,
    onNavigateToSignUp: () -> Unit,
) {
    composable<SignInRoute> {
        SignInScreen(
            onSignIn = onSignIn,
            onNavigateToSignUp = onNavigateToSignUp
        )
    }
}