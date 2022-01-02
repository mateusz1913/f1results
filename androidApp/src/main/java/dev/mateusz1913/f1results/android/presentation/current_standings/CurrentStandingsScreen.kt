package dev.mateusz1913.f1results.android.presentation.current_standings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import dev.mateusz1913.f1results.android.presentation.common.TabBar
import dev.mateusz1913.f1results.android.presentation.common.TabBarItem
import dev.mateusz1913.f1results.android.presentation.standings.ConstructorStandings
import dev.mateusz1913.f1results.android.presentation.standings.DriverStandings
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@ExperimentalPagerApi
@Composable
fun CurrentStandingsScreen(currentStandingsViewModel: CurrentStandingsViewModel = getViewModel()) {
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
                    TabBarItem("Drivers") {
                        composableScope.launch {
                            pagerState.animateScrollToPage(0)
                        }
                    },
                    TabBarItem("Constructors") {
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
                    0 -> DriverStandings(
                        standings = currentStandingsViewModel.driverStandings,
                        isRefreshing = currentStandingsViewModel.driverStandings != null && currentStandingsViewModel.isFetchingDriverStandings) {
                        currentStandingsViewModel.fetchDriverStandings()
                    }
                    1 -> ConstructorStandings(
                        standings = currentStandingsViewModel.constructorStandings,
                        isRefreshing = currentStandingsViewModel.constructorStandings != null && currentStandingsViewModel.isFetchingConstructorStandings) {
                        currentStandingsViewModel.fetchConstructorStandings()
                    }
                }
            }
        }
    }
}