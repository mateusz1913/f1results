package dev.mateusz1913.f1results.android.presentation.standings

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import dev.mateusz1913.f1results.datasource.data.standings.ConstructorStandingsType

@Composable
fun ConstructorStandings(standings: ConstructorStandingsType?, isRefreshing: Boolean, onRefresh: () -> Unit) {
    if (standings != null) {
        SwipeRefresh(rememberSwipeRefreshState(isRefreshing), onRefresh) {
            LazyColumn {
                items(standings.constructorStandings) {
                    Box(modifier = Modifier
                        .padding(4.dp)
                        .border(1.dp, MaterialTheme.colors.primary)) {
                        Text("${it.position}: ${it.constructor.name} - ${it.points}")
                    }
                }
            }
        }
    } else {
        Text("No constructor standings")
    }
}
