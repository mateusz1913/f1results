package dev.mateusz1913.f1results.android.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
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

val items = listOf(
    Screen.CurrentRaceResults,
    Screen.CurrentStandings,
    Screen.CurrentCalendar,
)

@Composable
fun Navigation() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    BottomNavigationItem(
                        icon = {
                            if (screen.icon != null) {
                                Icon(screen.icon, contentDescription = screen.iconContentDescription)
                            }
                        },
                        label = {
                            if (screen.label != null) {
                                Text(screen.label, fontSize = 10.sp)
                            }
                        },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                    )
                }
            }
        },
        topBar = { TopAppBar(title = { Text("F1Results") }) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.CurrentCalendar.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = Screen.CurrentRaceResults.route) {
                CurrentRaceResultsScreen()
            }
            composable(route = Screen.CurrentStandings.route) {
                CurrentStandingsScreen()
            }
            composable(route = Screen.CurrentCalendar.route) {
                CurrentCalendarScreen()
            }
            composable(
                route = Screen.CircuitScreen.route + "/{circuitId}",
                arguments = listOf(navArgument("circuitId") {
                    type = NavType.StringType
                })
            ) { navBackStackEntry ->
                val circuitId = navBackStackEntry.arguments?.getString("circuitId")
                requireNotNull(circuitId) { "circuitId parameter not found"}
                CircuitScreen(circuitId)
            }
            composable(
                route = Screen.ConstructorScreen.route + "/{constructorId}",
                arguments = listOf(navArgument("constructorId") {
                    type = NavType.StringType
                })
            ) {
                ConstructorScreen()
            }
            composable(
                route = Screen.DriverScreen.route + "/{driverId}",
                arguments = listOf(navArgument("driverId") {
                    type = NavType.StringType
                })
            ) { navBackStackEntry ->
                val driverId = navBackStackEntry.arguments?.getString("driverId")
                requireNotNull(driverId) { "driverId parameter not found" }
                DriverScreen(driverId)
            }
        }
    }
}