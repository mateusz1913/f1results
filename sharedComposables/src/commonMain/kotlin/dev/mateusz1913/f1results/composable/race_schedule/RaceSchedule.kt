package dev.mateusz1913.f1results.composable.race_schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.mateusz1913.f1results.composable.common.MaybeSwipeRefresh
import dev.mateusz1913.f1results.composable.common.RaceScheduleRow
import dev.mateusz1913.f1results.composable.common.verticalFadingEdge
import dev.mateusz1913.f1results.composable.navigation.LocalNavController
import dev.mateusz1913.f1results.datasource.data.race_schedule.RaceType

@Composable
fun RaceSchedule(raceScheduleList: Array<RaceType>?, isRefreshing: Boolean, onRefresh: () -> Unit) {
    if (raceScheduleList != null) {
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
                    raceScheduleList.map {
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 24.dp)
                                .clickable {
                                    navigationController.navigateToCircuitScreen(it.circuit.circuitId)
                                }
                        ) {
                            RaceScheduleRow(it)
                        }
                    }
                }
            }
        }
    } else {
        Text("No schedule")
    }
}
