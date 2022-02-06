package dev.mateusz1913.f1results.desktop.presentation.preview.common

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import dev.mateusz1913.f1results.composable.common.TabBar
import dev.mateusz1913.f1results.composable.common.TabBarItem

@Preview
@Composable
fun TabBarPreview() {
    TabBar(
        items = arrayOf(
            TabBarItem(label = "First label", onPress = {}),
            TabBarItem(label = "Second Label", onPress = {})
        )
    )
}