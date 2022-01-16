package dev.mateusz1913.f1results.desktop.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.EmojiEvents
import androidx.compose.material.icons.rounded.Event
import androidx.compose.material.icons.rounded.SportsScore
import androidx.compose.ui.graphics.vector.ImageVector
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import dev.mateusz1913.f1results.composable.navigation.Screen

sealed class ScreenConfig(
    override val route: String,
    override val icon: ImageVector? = null,
    override val iconContentDescription: String? = null,
    override val label: String? = null,
): Screen, Parcelable {
    @Parcelize
    object CurrentRaceResults: ScreenConfig(
        route = "currentRaceResults",
        icon = Icons.Rounded.SportsScore,
        iconContentDescription = "Checkered flag icon",
        label = "Last Results"
    )

    @Parcelize
    object CurrentStandings: ScreenConfig(
        route = "currentStandings",
        icon = Icons.Rounded.EmojiEvents,
        iconContentDescription = "Trophy icon",
        label = "Standings"
    )

    @Parcelize
    object CurrentCalendar: ScreenConfig(
        route ="currentCalendar",
        icon = Icons.Rounded.Event,
        iconContentDescription = "Calendar icon",
        label = "Calendar"
    )

    @Parcelize
    data class DriverScreen(val driverId: String): ScreenConfig(route = "driverScreen")

    @Parcelize
    data class CircuitScreen(val circuitId: String): ScreenConfig(route = "circuitScreen")

    @Parcelize
    data class ConstructorScreen(val constructorId: String): ScreenConfig(route = "constructorScreen")
}