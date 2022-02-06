package dev.mateusz1913.f1results.android.presentation.preview.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.mateusz1913.f1results.composable.common.ConstructorSeasonResults
import dev.mateusz1913.f1results.mocks.race01
import dev.mateusz1913.f1results.mocks.race02

@Preview(showBackground = true, widthDp = 400, heightDp = 600)
@Composable
fun ConstructorSeasonResultsLoadingPreview() {
    ConstructorSeasonResults(raceResultsList = arrayOf(), loading = true)
}

@Preview(showBackground = true, widthDp = 400, heightDp = 600)
@Composable
fun ConstructorSeasonResultsPreview() {
    ConstructorSeasonResults(
        raceResultsList = arrayOf(race01, race02),
        loading = false
    )
}
