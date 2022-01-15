package dev.mateusz1913.f1results.composable.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
actual fun MaybeSwipeRefresh(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier,
    swipeEnabled: Boolean,
    refreshTriggerDistance: Dp,
    indicatorAlignment: Alignment,
    indicatorPadding: PaddingValues,
    clipIndicatorToPadding: Boolean,
    content: @Composable () -> Unit
) {
    Column(modifier = modifier) {
        content()
    }
}
