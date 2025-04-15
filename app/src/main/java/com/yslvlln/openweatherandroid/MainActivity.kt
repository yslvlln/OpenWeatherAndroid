package com.yslvlln.openweatherandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.yslvlln.feature.auth.screens.signin.SignInRoute
import com.yslvlln.feature.auth.screens.signin.signInDestination
import com.yslvlln.feature.auth.screens.signup.SignUpRoute
import com.yslvlln.feature.auth.screens.signup.signUpDestination
import com.yslvlln.openweatherandroid.screens.MainRoute
import com.yslvlln.openweatherandroid.screens.mainDestination
import com.yslvlln.openweatherandroid.ui.theme.OpenWeatherAndroidTheme
import com.yslvlln.openweatherandroid.utils.navigateUpToRoot
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OpenWeatherAndroidTheme {
                val navController = rememberNavController()
                val state by viewModel.state.collectAsState()

                LaunchedEffect(Unit) {
                    viewModel.getUser()
                }

                LaunchedEffect(state) {
                    when (state) {
                        InitialNavigation.LANDING -> {
                            navController.navigateUpToRoot(SignInRoute)
                        }

                        InitialNavigation.HOME -> {
                            /**
                             * Do nothing because start destination is [MainRoute]
                             */
                        }

                        null -> {

                        }
                    }
                }

                RootNavigation(
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun RootNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = MainRoute
    ) {
        mainDestination()

        signInDestination(
            onSignIn = {
                navController.navigateUpToRoot(MainRoute)
            },
            onNavigateToSignUp = {
                navController.navigate(SignUpRoute)
            }
        )

        signUpDestination(
            onSignUp = {
                navController.navigateUpToRoot(MainRoute)
            },
            onNavigateUp = {
                navController.navigateUp()
            }
        )
    }
}