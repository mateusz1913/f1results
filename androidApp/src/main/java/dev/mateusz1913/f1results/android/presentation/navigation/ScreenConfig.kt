package dev.mateusz1913.f1results.android.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.EmojiEvents
import androidx.compose.material.icons.rounded.Event
import androidx.compose.material.icons.rounded.SportsScore
import androidx.compose.ui.graphics.vector.ImageVector
import dev.mateusz1913.f1results.composable.navigation.Screen

sealed class ScreenConfig(
    override val route: String,
    override val icon: ImageVector? = null,
    override val iconContentDescription: String? = null,
    override val label: String? = null
): Screen {
    object CurrentRaceResults: ScreenConfig(
        route = "currentRaceResults",
        icon = Icons.Rounded.SportsScore,
        iconContentDescription = "Checkered flag icon",
        label = "Last Results"
    )
    object CurrentStandings: ScreenConfig(
        route = "currentStandings",
        icon = Icons.Rounded.EmojiEvents,
        iconContentDescription = "Trophy icon",
        label = "Standings"
    )
    object CurrentCalendar: ScreenConfig(
        route = "currentCalendar",
        icon = Icons.Rounded.Event,
        iconContentDescription = "Calendar icon",
        label = "Calendar"
    )
    object DriverScreen: ScreenConfig(route = "driverScreen")
    object CircuitScreen: ScreenConfig(route = "circuitScreen")
    object ConstructorScreen: ScreenConfig(route = "constructorScreen")
}
