package dev.mateusz1913.f1results.desktop.presentation.preview.common

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import dev.mateusz1913.f1results.composable.common.DriverSeasonResults
import dev.mateusz1913.f1results.mocks.race01

@Preview
@Composable
fun DriverSeasonResultsPreviewLoading() {
    DriverSeasonResults(raceResultsList = arrayOf(), loading = true)
}

@Preview
@Composable
fun DriverSeasonResultsPreview() {
    DriverSeasonResults(
        raceResultsList = arrayOf(race01),
        loading = false
    )
}
