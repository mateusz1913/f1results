package dev.mateusz1913.f1results.composable.driver

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.mateusz1913.f1results.composable.common.DriverStandingsRow
import dev.mateusz1913.f1results.composable.common.DropdownButton
import dev.mateusz1913.f1results.composable.common.Loading
import dev.mateusz1913.f1results.composable.di.getViewModelInstance
import dev.mateusz1913.f1results.datasource.data.season_list.SeasonType
import dev.mateusz1913.f1results.datasource.data.standings.DriverStandingType
import dev.mateusz1913.f1results.viewmodel.DriverViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun DriverScreen(
    driverId: String,
    driverViewModel: DriverViewModel = getViewModelInstance(parameters = { parametersOf(driverId) })
) {
    val driverState = driverViewModel.driverState.collectAsState()
    val seasonsState = driverViewModel.seasonsState.collectAsState()
    val driverStandingState = driverViewModel.driverStandingState.collectAsState()
    val selectedSeasonState = driverViewModel.selectedSeasonState.collectAsState()
    val driver = driverState.value.driver
    val seasons = seasonsState.value.seasons
    val selectedSeason = selectedSeasonState.value
    val isLoading =
        (driverState.value.isFetching && driver == null) || (seasonsState.value.isFetching && seasons == null)
    if (isLoading) {
        Loading()
        return
    }
    if (driver == null) {
        Text("No driver")
        return
    }
    val scrollState = rememberScrollState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, top = 20.dp, end = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier
                    .padding(start = 0.dp, top = 2.dp, end = 10.dp, bottom = 2.dp)) {
                    Column(
                        modifier = Modifier
                            .size(50.dp)
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colors.onPrimary,
                                shape = RoundedCornerShape(25.dp)
                            ),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            "${driver.permanentNumber}",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Text(
                    driver.code,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.secondary
                )
            }
            InfoContainer {
                Text(
                    "${driver.givenName} ${driver.familyName}",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colors.onBackground
                )
            }
            InfoContainer {
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
                            append("Nationality: ")
                        }
                        append("${driver.nationality}")
                    },
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            InfoContainer {
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
                            append("Date of birth: ")
                        }
                        append("${driver.dateOfBirth}")
                    }
                )
            }
            if (seasons != null && selectedSeason != null) {
                SeasonsSummary(
                    seasons = seasons,
                    driverStanding = driverStandingState.value.driverStanding,
                    selectedSeason = selectedSeason
                ) { season ->
                    driverViewModel.fetchDriverStanding(season)
                }
            }
        }
    }
}

@Composable
fun InfoContainer(content: @Composable (ColumnScope.() -> Unit)) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        content = content
    )
}

@Composable
fun SeasonsSummary(
    seasons: Array<SeasonType>,
    driverStanding: DriverStandingType? = null,
    selectedSeason: String,
    onSelectedSeasonChange: (season: String) -> Unit
) {
    Column(modifier = Modifier.padding(start = 24.dp, top = 10.dp, end = 24.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Selected season: ")
            DropdownButton(
                items = seasons.map { it.season },
                selected = selectedSeason,
                onSelectedChange = onSelectedSeasonChange
            )
        }
        if (driverStanding != null) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text("Standings: ", fontSize = 18.sp, fontStyle = FontStyle.Italic)
                DriverStandingsRow(driverStanding = driverStanding, noDriverInfo = true)
            }
        }
    }
}