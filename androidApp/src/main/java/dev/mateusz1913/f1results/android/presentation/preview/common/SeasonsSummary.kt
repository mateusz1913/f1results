package dev.mateusz1913.f1results.android.presentation.preview.common

import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import dev.mateusz1913.f1results.composable.common.ConstructorSeasonsSummary
import dev.mateusz1913.f1results.composable.common.DriverSeasonsSummary
import dev.mateusz1913.f1results.datasource.data.season_list.SeasonType

@Preview(showBackground = true, widthDp = 400, heightDp = 200)
@Composable
fun ConstructorSeasonsSummaryPreview() {
    var selectedSeason by remember { mutableStateOf("2022") }
    ConstructorSeasonsSummary(
        seasons = arrayOf(
            SeasonType(season = "2022", url = ""),
            SeasonType(season = "2021", url = "")
        ),
        selectedSeason = selectedSeason,
        onSelectedSeasonChange = { newSelected -> selectedSeason = newSelected }
    )
}

@Preview(showBackground = true, widthDp = 400, heightDp = 200)
@Composable
fun DriverSeasonsSummaryPreview() {
    var selectedSeason by remember { mutableStateOf("2022") }
    DriverSeasonsSummary(
        seasons = arrayOf(
            SeasonType(season = "2022", url = ""),
            SeasonType(season = "2021", url = "")
        ),
        selectedSeason = selectedSeason,
        onSelectedSeasonChange = { newSelected -> selectedSeason = newSelected }
    )
}
