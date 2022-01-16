package dev.mateusz1913.f1results.desktop.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.animation.child.crossfadeScale
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.pop
import com.arkivanov.decompose.router.push
import com.arkivanov.decompose.router.replaceCurrent
import com.arkivanov.decompose.value.ValueObserver
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
    ScreenConfig.CurrentCalendar
)

@Composable
fun Navigation() {
    val router =
        rememberRouter<ScreenConfig>(initialConfiguration = { ScreenConfig.CurrentRaceResults })
    var activeChildInstance by remember { mutableStateOf<Any?>(null) }
    var hasPreviousScreen by remember { mutableStateOf(false) }
    val bottomBarVisibleState = rememberSaveable { mutableStateOf(true) }
    val topBarVisibleState = rememberSaveable { mutableStateOf(false) }
    DisposableEffect(null) {
        val valueObserver: ValueObserver<RouterState<ScreenConfig, Any>> = { it ->
            activeChildInstance = it.activeChild.instance
            hasPreviousScreen = it.backStack.isNotEmpty()
        }
        router.state.subscribe(valueObserver)
        onDispose {
            router.state.unsubscribe(valueObserver)
        }
    }
    Scaffold(
        bottomBar = {
            NavigationBottomBar(
                bottomBarVisibleState = bottomBarVisibleState,
                items = items,
                isItemSelected = { screen -> activeChildInstance == screen },
                onBottomItemClick = { screen ->
                    router.replaceCurrent(screen as ScreenConfig)
                }
            )
        },
        topBar = {
            NavigationTopBar(
                topBarVisibleState = topBarVisibleState,
                title = (activeChildInstance as ScreenConfig).route,
                shouldDisplayBackButton = hasPreviousScreen,
                onBackButtonPress = { router.pop() }
            )
        }
    ) { innerPadding ->
        val localNavController: NavigationController = object : NavigationController {
            override fun goBack() {
                router.pop()
            }

            override fun navigate(to: String) {
                val configuration = when (to) {
                    ScreenConfig.CurrentCalendar.route -> ScreenConfig.CurrentCalendar
                    ScreenConfig.CurrentRaceResults.route -> ScreenConfig.CurrentRaceResults
                    ScreenConfig.CurrentStandings.route -> ScreenConfig.CurrentStandings
                    else -> null
                }
                configuration?.let { router.push(it) }
            }

            override fun navigateToCircuitScreen(circuitId: String) {
                router.push(ScreenConfig.CircuitScreen(circuitId = circuitId))
            }

            override fun navigateToConstructorScreen(constructorId: String) {
                router.push(ScreenConfig.ConstructorScreen(constructorId = constructorId))
            }

            override fun navigateToDriverScreen(driverId: String) {
                router.push(ScreenConfig.DriverScreen(driverId = driverId))
            }
        }
        CompositionLocalProvider(LocalNavController provides localNavController) {
            Box(modifier = Modifier.padding(innerPadding)) {
                Children(
                    routerState = router.state,
                    animation = crossfadeScale()
                ) { screen ->
                    bottomBarVisibleState.value = when (screen.configuration) {
                        is ScreenConfig.CurrentCalendar, is ScreenConfig.CurrentRaceResults, is ScreenConfig.CurrentStandings -> true
                        else -> false
                    }
                    topBarVisibleState.value = when (screen.configuration) {
                        is ScreenConfig.CurrentCalendar, is ScreenConfig.CurrentRaceResults, is ScreenConfig.CurrentStandings -> false
                        else -> true
                    }
                    when (val configuration = screen.configuration) {
                        is ScreenConfig.CurrentRaceResults -> CurrentRaceResultsScreen()
                        is ScreenConfig.CurrentStandings -> CurrentStandingsScreen()
                        is ScreenConfig.CurrentCalendar -> CurrentCalendarScreen()
                        is ScreenConfig.CircuitScreen -> CircuitScreen(configuration.circuitId)
                        is ScreenConfig.ConstructorScreen -> ConstructorScreen(configuration.constructorId)
                        is ScreenConfig.DriverScreen -> DriverScreen(configuration.driverId)
                    }
                }
            }
        }
    }
}
