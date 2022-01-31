package dev.mateusz1913.f1results.composable.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.mateusz1913.f1results.datasource.data.season_list.SeasonType
import dev.mateusz1913.f1results.datasource.data.standings.ConstructorStandingType
import dev.mateusz1913.f1results.datasource.data.standings.DriverStandingType

@Composable
fun SeasonsSummary(
    seasons: Array<SeasonType>,
    selectedSeason: String,
    onSelectedSeasonChange: (season: String) -> Unit,
    content: @Composable () -> Unit
) {
    Column(modifier = Modifier.padding(top = 10.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Selected season: ")
            DropdownButton(
                items = seasons.map { it.season },
                selected = selectedSeason,
                onSelectedChange = onSelectedSeasonChange
            )
        }
        content()
    }
}

@Composable
fun ConstructorSeasonsSummary(
    constructorStanding: ConstructorStandingType? = null,
    seasons: Array<SeasonType>,
    selectedSeason: String,
    onSelectedSeasonChange: (season: String) -> Unit,
) {
    SeasonsSummary(seasons, selectedSeason, onSelectedSeasonChange) {
        if (constructorStanding != null) {
            Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)) {
                Text("Standings: ", fontSize = 18.sp, fontStyle = FontStyle.Italic)
                ConstructorStandingRow(constructorStanding = constructorStanding)
            }
        }
    }
}

@Composable
fun DriverSeasonsSummary(
    driverStanding: DriverStandingType? = null,
    seasons: Array<SeasonType>,
    selectedSeason: String,
    onSelectedSeasonChange: (season: String) -> Unit,
) {
    SeasonsSummary(seasons, selectedSeason, onSelectedSeasonChange) {
        if (driverStanding != null) {
            Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)) {
                Text("Standings: ", fontSize = 18.sp, fontStyle = FontStyle.Italic)
                DriverStandingRow(driverStanding = driverStanding, noDriverInfo = true)
            }
        }
    }
}
