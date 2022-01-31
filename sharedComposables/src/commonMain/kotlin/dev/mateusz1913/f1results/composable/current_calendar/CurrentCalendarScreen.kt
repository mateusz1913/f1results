package dev.mateusz1913.f1results.composable.current_calendar

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.mateusz1913.f1results.composable.di.getViewModelInstance
import dev.mateusz1913.f1results.composable.race_schedule.RaceSchedule
import dev.mateusz1913.f1results.viewmodel.CurrentCalendarViewModel

@Composable
fun CurrentCalendarScreen(currentCalendarViewModel: CurrentCalendarViewModel = getViewModelInstance()) {
    val raceScheduleState = currentCalendarViewModel.currentRaceScheduleState.collectAsState()
    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxWidth().height(60.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "Current Calendar",
                color = MaterialTheme.colors.primary,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
        RaceSchedule(
            raceScheduleList = raceScheduleState.value.currentRaceScheduleList,
            isRefreshing = raceScheduleState.value.currentRaceScheduleList != null && raceScheduleState.value.isFetching,
            onRefresh = {
                currentCalendarViewModel.fetchCurrentRaceSchedule()
            }
        )
    }
}
