package dev.mateusz1913.f1results.android.presentation.current_race_results

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import dev.mateusz1913.f1results.android.presentation.common.TabBar
import dev.mateusz1913.f1results.android.presentation.common.TabBarItem
import dev.mateusz1913.f1results.android.presentation.qualifying_results.QualifyingResults
import dev.mateusz1913.f1results.android.presentation.race_results.RaceResults
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@ExperimentalPagerApi
@Composable
fun CurrentRaceResultsScreen(currentRaceResultsViewModel: CurrentRaceResultsViewModel = getViewModel()) {
    val pagerState = rememberPagerState()
    val composableScope = rememberCoroutineScope()
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            TabBar(
                items = arrayOf(
                    TabBarItem("Race") {
                        composableScope.launch {
                            pagerState.animateScrollToPage(0)
                        }
                    },
                    TabBarItem("Qualification") {
                        composableScope.launch {
                            pagerState.animateScrollToPage(1)
                        }
                    }
                )
            )
            HorizontalPager(
                count = 2,
                modifier = Modifier.fillMaxSize(),
                state = pagerState
            ) { page ->
                when (page) {
                    0 -> RaceResults(
                        results = currentRaceResultsViewModel.raceResults,
                        isRefreshing = currentRaceResultsViewModel.raceResults != null && currentRaceResultsViewModel.isFetchingRaceResults) {
                        currentRaceResultsViewModel.fetchCurrentRaceResults()
                    }
                    1 -> QualifyingResults(
                        results = currentRaceResultsViewModel.qualifyingResults,
                        isRefreshing = currentRaceResultsViewModel.qualifyingResults != null &&  currentRaceResultsViewModel.isFetchingQualifyingResults) {
                        currentRaceResultsViewModel.fetchCurrentQualifyingResults()
                    }
                }
            }

        }
    }
}
