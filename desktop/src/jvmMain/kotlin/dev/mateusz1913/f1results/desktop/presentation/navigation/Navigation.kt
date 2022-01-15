package dev.mateusz1913.f1results.desktop.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.jetbrains.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.animation.child.crossfadeScale
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.ValueObserver
import dev.mateusz1913.f1results.composable.current_calendar.CurrentCalendarScreen
import dev.mateusz1913.f1results.composable.current_race_results.CurrentRaceResultsScreen
import dev.mateusz1913.f1results.composable.current_standings.CurrentStandingsScreen

val items = listOf(
    Screen.CurrentRaceResults,
    Screen.CurrentStandings,
    Screen.CurrentCalendar
)

@Composable
fun Navigation() {
    val router = rememberRouter<Screen>(initialConfiguration = { Screen.CurrentRaceResults })
    var activeChildInstance by remember { mutableStateOf<Any?>(null) }
    DisposableEffect(null) {
        val valueObserver: ValueObserver<RouterState<Screen, Any>> = {
            activeChildInstance = it.activeChild.instance
        }
        router.state.subscribe(valueObserver)
        onDispose {
            router.state.unsubscribe(valueObserver)
        }
    }
    Scaffold(
        bottomBar = {
            BottomNavigation {
                items.forEach { screen ->
                    BottomNavigationItem(
                        label = {
                            if (screen.label != null) {
                                Text(screen.label, fontSize = 10.sp)
                            }
                        },
                        icon = {
                            if (screen.icon != null) {
                                Icon(
                                    screen.icon,
                                    contentDescription = screen.iconContentDescription
                                )
                            }
                        },
                        selected = activeChildInstance == screen,
                        onClick = {
                            router.navigateInBottomNavigation(screen)
                        },
                        selectedContentColor = MaterialTheme.colors.primary
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Children(
                routerState = router.state,
                animation = crossfadeScale()
            ) { screen ->
                when (val configuration = screen.configuration) {
                    is Screen.CurrentRaceResults -> CurrentRaceResultsScreen()
                    is Screen.CurrentStandings -> CurrentStandingsScreen()
                    is Screen.CurrentCalendar -> CurrentCalendarScreen()
                }
            }
        }
    }
}
