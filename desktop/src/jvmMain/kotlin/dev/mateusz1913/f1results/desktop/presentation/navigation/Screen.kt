package dev.mateusz1913.f1results.desktop.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.EmojiEvents
import androidx.compose.material.icons.rounded.Event
import androidx.compose.material.icons.rounded.SportsScore
import androidx.compose.ui.graphics.vector.ImageVector
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

sealed class Screen(
    val route: String,
    val icon: ImageVector? = null,
    val iconContentDescription: String? = null,
    val label: String? = null
): Parcelable {
    @Parcelize
    object CurrentRaceResults: Screen(
        route = "currentRaceResults",
        icon = Icons.Rounded.SportsScore,
        iconContentDescription = "Checkered flag icon",
        label = "Last Results"
    )

    @Parcelize
    object CurrentStandings: Screen(
        route = "currentStandings",
        icon = Icons.Rounded.EmojiEvents,
        iconContentDescription = "Trophy icon",
        label = "Standings"
    )

    @Parcelize
    object CurrentCalendar: Screen(
        route ="currentCalendar",
        icon = Icons.Rounded.Event,
        iconContentDescription = "Calendar icon",
        label = "Calendar"
    )

    @Parcelize
    object DriverScreen: Screen("driverScreen")

    @Parcelize
    object CircuitScreen: Screen("circuitScreen")

    @Parcelize
    object ConstructorScreen: Screen("constructorScreen")
}