package dev.mateusz1913.f1results.desktop.presentation.preview.common

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import dev.mateusz1913.f1results.composable.common.RaceScheduleRow
import dev.mateusz1913.f1results.mocks.raceSchedule01

@Preview
@Composable
fun RaceScheduleRowPreview() {
    RaceScheduleRow(race = raceSchedule01)
}
