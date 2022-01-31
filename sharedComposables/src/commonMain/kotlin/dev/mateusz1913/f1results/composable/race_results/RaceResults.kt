package dev.mateusz1913.f1results.composable.race_results

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.mateusz1913.f1results.composable.common.DriverResultRow
import dev.mateusz1913.f1results.composable.common.MaybeSwipeRefresh
import dev.mateusz1913.f1results.composable.common.verticalFadingEdge
import dev.mateusz1913.f1results.composable.navigation.LocalNavController
import dev.mateusz1913.f1results.datasource.data.race_results.RaceWithResultsType

@Composable
fun RaceResults(results: RaceWithResultsType?, isRefreshing: Boolean, onRefresh: () -> Unit) {
    if (results != null) {
        val navigationController = LocalNavController.current
        MaybeSwipeRefresh(isRefreshing, onRefresh) {
            val scrollState = rememberScrollState()
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colors.background)
                    .verticalFadingEdge(scrollState = scrollState, length = 30.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(state = scrollState),
                ) {
                    results.results.map {
                        Box(modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp)) {
                            Card(
                                elevation = 2.dp,
                                modifier = Modifier.clickable {
                                    navigationController.navigateToDriverScreen(it.driver.driverId)
                                }
                            ) {
                                DriverResultRow(it)
                            }
                        }
                    }
                }
            }
        }
    } else {
        Text("No race results")
    }
}
