package dev.mateusz1913.f1results.composable.current_standings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import dev.mateusz1913.f1results.composable.common.TwoColumnLayout
import dev.mateusz1913.f1results.composable.common.TwoColumnLayoutItem
import dev.mateusz1913.f1results.composable.di.getViewModelInstance
import dev.mateusz1913.f1results.composable.standings.ConstructorStandings
import dev.mateusz1913.f1results.composable.standings.DriverStandings
import dev.mateusz1913.f1results.viewmodel.CurrentStandingsViewModel

@Composable
fun CurrentStandingsScreen(currentStandingsViewModel: CurrentStandingsViewModel = getViewModelInstance()) {
    val driverStandingsState = currentStandingsViewModel.driverStandingsState.collectAsState()
    val constructorStandingsState = currentStandingsViewModel.constructorStandingsState.collectAsState()
    Box(modifier = Modifier.fillMaxSize()) {
        TwoColumnLayout(
            firstItem = TwoColumnLayoutItem(
                item = {
                    DriverStandings(
                        standings = driverStandingsState.value.driverStandings,
                        isRefreshing = driverStandingsState.value.driverStandings != null && driverStandingsState.value.isFetching) {
                        currentStandingsViewModel.fetchDriverStandings()
                    }
                },
                title = "Drivers"
            ),
            secondItem = TwoColumnLayoutItem(
                item = {
                    ConstructorStandings(
                        standings = constructorStandingsState.value.constructorStandings,
                        isRefreshing = constructorStandingsState.value.constructorStandings != null && constructorStandingsState.value.isFetching) {
                        currentStandingsViewModel.fetchConstructorStandings()
                    }
                },
                title = "Constructors"
            )
        )
    }
}
