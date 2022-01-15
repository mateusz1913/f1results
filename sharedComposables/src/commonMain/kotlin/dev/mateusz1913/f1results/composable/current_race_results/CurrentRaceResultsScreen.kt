package dev.mateusz1913.f1results.composable.current_race_results

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import dev.mateusz1913.f1results.composable.common.TwoColumnLayout
import dev.mateusz1913.f1results.composable.common.TwoColumnLayoutItem
import dev.mateusz1913.f1results.composable.di.getViewModelInstance
import dev.mateusz1913.f1results.composable.qualifying_results.QualifyingResults
import dev.mateusz1913.f1results.composable.race_results.RaceResults
import dev.mateusz1913.f1results.viewmodel.CurrentRaceResultsViewModel

@Composable
fun CurrentRaceResultsScreen(currentRaceResultsViewModel: CurrentRaceResultsViewModel = getViewModelInstance()) {
    val raceResultsState = currentRaceResultsViewModel.raceResultsState.collectAsState()
    val qualifyingResultsState = currentRaceResultsViewModel.qualifyingResultsState.collectAsState()
    Box(modifier = Modifier.fillMaxSize()) {
        TwoColumnLayout(
            firstItem = TwoColumnLayoutItem(
                item = {
                    RaceResults(
                        results = raceResultsState.value.raceResults,
                        isRefreshing = raceResultsState.value.raceResults != null && raceResultsState.value.isFetching
                    ) {
                        currentRaceResultsViewModel.fetchCurrentRaceResults()
                    }
                },
                title = "Race"
            ),
            secondItem = TwoColumnLayoutItem(
                item = {
                    QualifyingResults(
                        results = qualifyingResultsState.value.qualifyingResults,
                        isRefreshing = qualifyingResultsState.value.qualifyingResults != null && qualifyingResultsState.value.isFetching
                    ) {
                        currentRaceResultsViewModel.fetchCurrentQualifyingResults()
                    }
                },
                title = "Qualification"
            )
        )
    }
}
