package dev.mateusz1913.f1results.composable.constructor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.mateusz1913.f1results.composable.common.*
import dev.mateusz1913.f1results.composable.di.getViewModelInstance
import dev.mateusz1913.f1results.viewmodel.ConstructorViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ConstructorScreen(
    constructorId: String,
    constructorViewModel: ConstructorViewModel = getViewModelInstance(parameters = {
        parametersOf(
            constructorId
        )
    })
) {
    val constructorState = constructorViewModel.constructorState.collectAsState()
    val seasonsState = constructorViewModel.seasonsState.collectAsState()
    val constructorStandingState = constructorViewModel.constructorStandingState.collectAsState()
    val selectedSeasonState = constructorViewModel.selectedSeasonState.collectAsState()
    val raceResultsState = constructorViewModel.constructorSeasonRaceResultsState.collectAsState()
    val constructor = constructorState.value.constructor
    val seasons = seasonsState.value.seasons
    val selectedSeason = selectedSeasonState.value
    val raceResults = raceResultsState.value.raceResults
    val isLoading = (constructorState.value.isFetching && constructor == null) || (seasonsState.value.isFetching && seasons == null)
    if (isLoading) {
        Loading()
        return
    }
    if (constructor == null) {
        Text("No constructor")
        return
    }
    val scrollState = rememberScrollState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
            .verticalFadingEdge(scrollState = scrollState, length = 30.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = scrollState),
        ) {
            InfoContainer(modifier = Modifier.padding(top = 20.dp)) {
                Text(constructor.name, fontSize = 30.sp, fontWeight = FontWeight.SemiBold)
            }
            InfoContainer {
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
                            append("Nationality: ")
                        }
                        append(constructor.nationality)
                    },
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            if (seasons != null && selectedSeason != null) {
                ConstructorSeasonsSummary(
                    constructorStanding = constructorStandingState.value.constructorStanding,
                    seasons = seasons,
                    selectedSeason = selectedSeason,
                ) { season ->
                    constructorViewModel.fetchConstructorStanding(season)
                }
            }
            ConstructorSeasonResults(raceResults, raceResultsState.value.isFetching)
        }
    }
}
