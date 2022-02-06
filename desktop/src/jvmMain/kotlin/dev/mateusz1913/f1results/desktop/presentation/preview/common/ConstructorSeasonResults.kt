package dev.mateusz1913.f1results.desktop.presentation.preview.common

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import dev.mateusz1913.f1results.composable.common.ConstructorSeasonResults
import dev.mateusz1913.f1results.mocks.race01
import dev.mateusz1913.f1results.mocks.race02

@Preview
@Composable
fun ConstructorSeasonResultsLoadingPreview() {
    ConstructorSeasonResults(raceResultsList = arrayOf(), loading = true)
}

@Preview
@Composable
fun ConstructorSeasonResultsPreview() {
    ConstructorSeasonResults(
        raceResultsList = arrayOf(race01, race02),
        loading = false
    )
}
