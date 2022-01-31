package dev.mateusz1913.f1results.composable.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.mateusz1913.f1results.datasource.data.race_results.RaceWithResultsType

@Composable
fun DriverSeasonResults(raceResultsList: Array<RaceWithResultsType>?, loading: Boolean) {
    if (loading) {
        Column(modifier = Modifier.fillMaxSize().padding(vertical = 24.dp)) {
            Loading()
        }
        return
    }
    Column(modifier = Modifier.fillMaxSize().padding(vertical = 24.dp)) {
        raceResultsList?.map {
            Box(modifier = Modifier.padding(horizontal = 24.dp, vertical = 5.dp)) {
                Card(elevation = 2.dp) {
                    Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)) {
                        val firstDriverResult = it.results[0]
                        val firstDriverStartingPosition = when (firstDriverResult.grid) {
                            "0" -> "Pit lane"
                            else -> firstDriverResult.grid
                        }
                        Text(
                            it.raceName,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                        Text(
                            buildAnnotatedString {
                                withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
                                    append("Started: ")
                                }
                                append(firstDriverStartingPosition)
                            },
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(vertical = 5.dp)
                        )
                        DriverResultRow(firstDriverResult, noDriverInfo = true)
                    }
                }
            }
        }
    }
}
