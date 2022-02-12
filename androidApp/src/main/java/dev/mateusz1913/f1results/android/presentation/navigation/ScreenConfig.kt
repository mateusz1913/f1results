package dev.mateusz1913.f1results.android.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.EmojiEvents
import androidx.compose.material.icons.rounded.Event
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.material.icons.rounded.SportsScore
import androidx.compose.ui.graphics.vector.ImageVector
import dev.mateusz1913.f1results.composable.navigation.Screen

sealed class ScreenConfig(
    override val route: String,
    override val icon: ImageVector? = null,
    override val iconContentDescription: String? = null,
    override val label: String? = null,
    override val topBarTitle: String? = null
) : Screen {
    object CurrentRaceResults : ScreenConfig(
        route = "currentRaceResults",
        icon = Icons.Rounded.SportsScore,
        iconContentDescription = "Checkered flag icon",
        label = "Last Results"
    )

    object CurrentStandings : ScreenConfig(
        route = "currentStandings",
        icon = Icons.Rounded.EmojiEvents,
        iconContentDescription = "Trophy icon",
        label = "Standings"
    )

    object CurrentCalendar : ScreenConfig(
        route = "currentCalendar",
        icon = Icons.Rounded.Event,
        iconContentDescription = "Calendar icon",
        label = "Calendar"
    )

    object Explore : ScreenConfig(
        route = "explore",
        icon = Icons.Rounded.MoreHoriz,
        iconContentDescription = "More icon",
        label = "Explore"
    )

    object DriverScreen : ScreenConfig(route = "driverScreen", topBarTitle = "Driver screen")
    object CircuitScreen : ScreenConfig(route = "circuitScreen", topBarTitle = "Circuit screen")
    object ConstructorScreen :
        ScreenConfig(route = "constructorScreen", topBarTitle = "Constructor screen")
}
