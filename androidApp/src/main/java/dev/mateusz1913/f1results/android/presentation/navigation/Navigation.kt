package dev.mateusz1913.f1results.android.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dev.mateusz1913.f1results.composable.circuit.CircuitScreen
import dev.mateusz1913.f1results.composable.constructor.ConstructorScreen
import dev.mateusz1913.f1results.composable.current_calendar.CurrentCalendarScreen
import dev.mateusz1913.f1results.composable.current_race_results.CurrentRaceResultsScreen
import dev.mateusz1913.f1results.composable.current_standings.CurrentStandingsScreen
import dev.mateusz1913.f1results.composable.driver.DriverScreen
import dev.mateusz1913.f1results.composable.navigation.LocalNavController
import dev.mateusz1913.f1results.composable.navigation.NavigationBottomBar
import dev.mateusz1913.f1results.composable.navigation.NavigationController
import dev.mateusz1913.f1results.composable.navigation.NavigationTopBar

val items = listOf(
    ScreenConfig.CurrentRaceResults,
    ScreenConfig.CurrentStandings,
    ScreenConfig.CurrentCalendar,
)

@Composable
fun Navigation() {
    val bottomBarVisibleState = rememberSaveable { mutableStateOf(true) }
    val topBarVisibleState = rememberSaveable { mutableStateOf(false) }
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            NavigationBottomBar(
                bottomBarVisibleState = bottomBarVisibleState,
                items = items,
                isItemSelected = { screen ->
                    currentDestination?.hierarchy?.any { it.route == screen.route } == true
                },
                onBottomItemClick = { screen ->
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        },
        topBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            val title = when {
                currentDestination?.route?.contains(ScreenConfig.CircuitScreen.route) == true -> {
                    "Circuit screen"
                }
                currentDestination?.route?.contains(ScreenConfig.ConstructorScreen.route) == true -> {
                    "Constructor screen"
                }
                currentDestination?.route?.contains(ScreenConfig.DriverScreen.route) == true -> {
                    "Driver screen"
                }
                else -> {
                    ""
                }
            }
            NavigationTopBar(
                topBarVisibleState = topBarVisibleState,
                title = title,
                shouldDisplayBackButton = navController.previousBackStackEntry != null,
                onBackButtonPress = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        val localNavController: NavigationController = object: NavigationController {
            override fun goBack() {
                navController.popBackStack()
            }

            override fun navigate(to: String) {
                navController.navigate(to)
            }

            override fun navigateToCircuitScreen(circuitId: String) {
                navController.navigate("circuitScreen/$circuitId")
            }

            override fun navigateToConstructorScreen(constructorId: String) {
                navController.navigate("constructorScreen/$constructorId")
            }

            override fun navigateToDriverScreen(driverId: String) {
                navController.navigate("driverScreen/$driverId")
            }
        }
        CompositionLocalProvider(LocalNavController provides localNavController) {
            NavHost(
                navController = navController,
                startDestination = ScreenConfig.CurrentRaceResults.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(route = ScreenConfig.CurrentRaceResults.route) {
                    bottomBarVisibleState.value = true
                    topBarVisibleState.value = false
                    CurrentRaceResultsScreen()
                }
                composable(route = ScreenConfig.CurrentStandings.route) {
                    bottomBarVisibleState.value = true
                    topBarVisibleState.value = false
                    CurrentStandingsScreen()
                }
                composable(route = ScreenConfig.CurrentCalendar.route) {
                    bottomBarVisibleState.value = true
                    topBarVisibleState.value = false
                    CurrentCalendarScreen()
                }
                composable(
                    route = ScreenConfig.CircuitScreen.route + "/{circuitId}",
                    arguments = listOf(navArgument("circuitId") {
                        type = NavType.StringType
                    })
                ) { navBackStackEntry ->
                    bottomBarVisibleState.value = false
                    topBarVisibleState.value = true
                    val circuitId = navBackStackEntry.arguments?.getString("circuitId")
                    requireNotNull(circuitId) { "circuitId parameter not found"}
                    CircuitScreen(circuitId)
                }
                composable(
                    route = ScreenConfig.ConstructorScreen.route + "/{constructorId}",
                    arguments = listOf(navArgument("constructorId") {
                        type = NavType.StringType
                    })
                ) { navBackStackEntry ->
                    bottomBarVisibleState.value = false
                    topBarVisibleState.value = true
                    val constructorId = navBackStackEntry.arguments?.getString("constructorId")
                    requireNotNull(constructorId) { "constructorId parameter not found" }
                    ConstructorScreen(constructorId)
                }
                composable(
                    route = ScreenConfig.DriverScreen.route + "/{driverId}",
                    arguments = listOf(navArgument("driverId") {
                        type = NavType.StringType
                    })
                ) { navBackStackEntry ->
                    bottomBarVisibleState.value = false
                    topBarVisibleState.value = true
                    val driverId = navBackStackEntry.arguments?.getString("driverId")
                    requireNotNull(driverId) { "driverId parameter not found" }
                    DriverScreen(driverId)
                }
            }
        }
    }
}