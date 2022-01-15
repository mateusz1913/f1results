package dev.mateusz1913.f1results.composable.current_calendar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import dev.mateusz1913.f1results.composable.di.getViewModelInstance
import dev.mateusz1913.f1results.composable.race_schedule.RaceSchedule
import dev.mateusz1913.f1results.viewmodel.CurrentCalendarViewModel

@Composable
fun CurrentCalendarScreen(currentCalendarViewModel: CurrentCalendarViewModel = getViewModelInstance()) {
    val raceScheduleState = currentCalendarViewModel.currentRaceScheduleState.collectAsState()
    Box(modifier = Modifier.fillMaxSize()) {
        RaceSchedule(
            raceScheduleList = raceScheduleState.value.currentRaceScheduleList,
            isRefreshing = raceScheduleState.value.currentRaceScheduleList != null && raceScheduleState.value.isFetching,
            onRefresh = {
                currentCalendarViewModel.fetchCurrentRaceSchedule()
            }
        )
    }
}
