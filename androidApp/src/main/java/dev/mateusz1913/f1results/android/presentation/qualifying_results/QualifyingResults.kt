package dev.mateusz1913.f1results.android.presentation.qualifying_results

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
import dev.mateusz1913.f1results.datasource.data.qualifying_results.RaceWithQualifyingResultsType

@Composable
fun QualifyingResults(results: RaceWithQualifyingResultsType?, isRefreshing: Boolean, onRefresh: () -> Unit) {
    if (results != null) {
        SwipeRefresh(rememberSwipeRefreshState(isRefreshing), onRefresh) {
            LazyColumn {
                items(results.qualifyingResults) {
                    Box(modifier = Modifier
                        .padding(4.dp)
                        .border(1.dp, MaterialTheme.colors.primary)) {
                        Text("${it.position}: ${it.driver.givenName} ${it.driver.familyName} - ${it.constructor.name}")
                    }
                }
            }
        }
    } else {
        Text("No qualifying results")
    }
}