package dev.mateusz1913.f1results.composable.standings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.mateusz1913.f1results.composable.common.ConstructorStandingRow
import dev.mateusz1913.f1results.composable.common.MaybeSwipeRefresh
import dev.mateusz1913.f1results.composable.common.verticalFadingEdge
import dev.mateusz1913.f1results.composable.navigation.LocalNavController
import dev.mateusz1913.f1results.datasource.data.standings.ConstructorStandingsType

@Composable
fun ConstructorStandings(
    standings: ConstructorStandingsType?,
    isRefreshing: Boolean,
    onRefresh: () -> Unit
) {
    if (standings != null) {
        val navigationController = LocalNavController.current
        MaybeSwipeRefresh(isRefreshing, onRefresh) {
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
                    standings.constructorStandings.map {
                        Box(modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp)) {
                            Card(
                                elevation = 2.dp,
                                modifier = Modifier.clickable {
                                    navigationController.navigateToConstructorScreen(it.constructor.constructorId)
                                }
                            ) {
                                ConstructorStandingRow(it)
                            }
                        }
                    }
                }
            }
        }
    } else {
        Text("No constructor standings")
    }
}
