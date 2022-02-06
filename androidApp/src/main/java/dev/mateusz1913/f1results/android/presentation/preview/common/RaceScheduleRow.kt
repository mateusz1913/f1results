package dev.mateusz1913.f1results.android.presentation.preview.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.mateusz1913.f1results.composable.common.RaceScheduleRow
import dev.mateusz1913.f1results.mocks.raceSchedule01

@Preview(showBackground = true, widthDp = 400, heightDp = 100)
@Composable
fun RaceScheduleRowPreview() {
    RaceScheduleRow(race = raceSchedule01)
}
