package com.yslvlln.openweatherandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.yslvlln.feature.auth.SignInNavigation
import com.yslvlln.feature.auth.SignInScreen
import com.yslvlln.feature.auth.signInDestination
import com.yslvlln.openweatherandroid.screens.MainRoute
import com.yslvlln.openweatherandroid.screens.mainDestination
import com.yslvlln.openweatherandroid.ui.theme.OpenWeatherAndroidTheme
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
                            navController.navigate(SignInNavigation) {
                                popUpTo(SignInNavigation)
                            }
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
            onSignIn = {},
            onNavigateToSignUp = {}
        )
    }
}