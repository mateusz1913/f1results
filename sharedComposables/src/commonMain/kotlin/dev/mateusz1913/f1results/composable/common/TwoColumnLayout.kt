package dev.mateusz1913.f1results.composable.common

import androidx.compose.runtime.Composable

@Composable
expect fun TwoColumnLayout(firstItem: TwoColumnLayoutItem, secondItem: TwoColumnLayoutItem)

data class TwoColumnLayoutItem(
    val item: @Composable () -> Unit,
    val title: String
)
