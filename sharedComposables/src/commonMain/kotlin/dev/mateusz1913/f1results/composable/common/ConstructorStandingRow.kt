package dev.mateusz1913.f1results.composable.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.mateusz1913.f1results.datasource.data.standings.ConstructorStandingType

@Composable
fun ConstructorStandingRow(
    constructorStanding: ConstructorStandingType,
    noConstructorInfo: Boolean = false
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .clickable(enabled = !noConstructorInfo) {
                // Handle navigation to driver screen
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        StandingPosition(constructorStanding.position)
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                constructorStanding.constructor.name,
                modifier = Modifier.padding(
                    start = 10.dp,
                    top = 2.dp,
                    end = 10.dp,
                    bottom = 2.dp
                )
            )
        }
        StandingScore(constructorStanding.points, constructorStanding.wins)
    }
}