package dev.mateusz1913.f1results.android.presentation.preview.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.mateusz1913.f1results.composable.common.DriverSeasonResults
import dev.mateusz1913.f1results.mocks.race01

@Preview(showBackground = true, widthDp = 400, heightDp = 400)
@Composable
fun DriverSeasonResultsPreviewLoading() {
    DriverSeasonResults(raceResultsList = arrayOf(), loading = true)
}

@Preview(showBackground = true, widthDp = 400, heightDp = 400)
@Composable
fun DriverSeasonResultsPreview() {
    DriverSeasonResults(
        raceResultsList = arrayOf(race01),
        loading = false
    )
}
