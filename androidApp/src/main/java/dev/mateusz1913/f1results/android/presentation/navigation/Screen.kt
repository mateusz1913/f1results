package dev.mateusz1913.f1results.android.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.EmojiEvents
import androidx.compose.material.icons.rounded.Event
import androidx.compose.material.icons.rounded.SportsScore
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val icon: ImageVector? = null,
    val iconContentDescription: String? = null,
    val label: String? = null
) {
    object CurrentRaceResults: Screen(
        "currentRaceResults",
        Icons.Rounded.SportsScore,
        "Checkered flag icon",
        "Last Results"
    )
    object CurrentStandings: Screen(
        "currentStandings",
        Icons.Rounded.EmojiEvents,
        "Trophy icon",
        "Standings"
    )
    object CurrentCalendar: Screen(
        "currentCalendar",
        Icons.Rounded.Event,
        "Calendar icon",
        "Calendar"
    )
    object DriverScreen: Screen("driverScreen")
    object CircuitScreen: Screen("circuitScreen")
    object ConstructorScreen: Screen("constructorScreen")
}
