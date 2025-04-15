package com.yslvlln.openweatherandroid.screens

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.yslvlln.openweatherandroid.screens.history.WeatherHistoryRoute
import com.yslvlln.openweatherandroid.screens.history.historyDestination
import com.yslvlln.openweatherandroid.screens.home.HomeRoute
import com.yslvlln.openweatherandroid.screens.home.homeDestination

data class MainScreenTopLevelRoute<T : Any>(val name: String, val route: T?, val icon: ImageVector)

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
) {
    val topLevelRoutes = listOf(
        MainScreenTopLevelRoute(
            "Home",
            HomeRoute,
            Icons.Default.Home
        ),
        MainScreenTopLevelRoute(
            "History",
            WeatherHistoryRoute,
            Icons.Default.DateRange
        )
    )

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets(0.dp), // we want the nav host routes to handle insets
        bottomBar = {
            val borderColor = MaterialTheme.colorScheme.secondaryContainer

            NavigationBar(
                modifier = Modifier.drawWithContent {
                    drawContent()
                    drawLine(
                        color = borderColor,
                        start = Offset.Zero,
                        end = Offset(size.width, 0f),
                        strokeWidth = 1.dp.toPx()
                    )
                },
                containerColor = MaterialTheme.colorScheme.surfaceContainer
            ) {
                topLevelRoutes.forEach { topLevelRoute ->

                    val isSelected = topLevelRoute.route?.let { route ->
                        currentDestination?.hierarchy?.any { it.hasRoute(route::class) } == true
                    } ?: false

                    NavigationBarItem(
                        colors = NavigationBarItemDefaults.colors(),
                        icon = {
                            Icon(
                                topLevelRoute.icon,
                                contentDescription = topLevelRoute.name
                            )
                        },
                        label = {
                            Text(
                                topLevelRoute.name,
                                style = MaterialTheme.typography.labelMedium
                            )
                        },
                        selected = isSelected,
                        onClick = {
                            topLevelRoute.route?.let { route ->
                                navController.navigate(route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier
                .consumeWindowInsets(innerPadding)
                .padding(innerPadding),
            navController = navController,
            startDestination = HomeRoute,
        ) {
            homeDestination()
            historyDestination()
        }
    }
}