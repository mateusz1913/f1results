package dev.mateusz1913.f1results.android.presentation.preview.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.mateusz1913.f1results.composable.common.TabBar
import dev.mateusz1913.f1results.composable.common.TabBarItem

@Preview(showBackground = true)
@Composable
fun TabBarPreview() {
    TabBar(
        items = arrayOf(
            TabBarItem(label = "First label", onPress = {}),
            TabBarItem(label = "Second Label", onPress = {})
        )
    )
}
