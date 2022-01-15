package dev.mateusz1913.f1results.composable.standings

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.mateusz1913.f1results.composable.common.MaybeSwipeRefresh
import dev.mateusz1913.f1results.datasource.data.standings.DriverStandingsType

@Composable
fun DriverStandings(standings: DriverStandingsType?, isRefreshing: Boolean, onRefresh: () -> Unit) {
    if (standings != null) {
        MaybeSwipeRefresh(isRefreshing, onRefresh) {
            LazyColumn {
                items(standings.driverStandings) {
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .border(1.dp, MaterialTheme.colors.primary)
                    ) {
                        Text("${it.positionText}: ${it.driver.givenName} ${it.driver.familyName} - ${
                            it.constructors.joinToString(
                                " / "
                            ) { it.name }
                        }")
                    }
                }
            }
        }
    } else {
        Text("No driver standings")
    }
}
