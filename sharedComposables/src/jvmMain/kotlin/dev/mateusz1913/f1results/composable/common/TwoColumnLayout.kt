package dev.mateusz1913.f1results.composable.common

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
actual fun TwoColumnLayout(firstItem: TwoColumnLayoutItem, secondItem: TwoColumnLayoutItem) {
    BoxWithConstraints {
        if (maxWidth.value < 700) {
            var selectedItem by remember { mutableStateOf(0) }
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                TabBar(
                    items = arrayOf(
                        TabBarItem(firstItem.title) {
                            selectedItem = 0
                        },
                        TabBarItem(secondItem.title) {
                            selectedItem = 1
                        }
                    )
                )
                when (selectedItem) {
                    0 -> firstItem.item()
                    1 -> secondItem.item()
                }
            }
        } else {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    firstItem.item()
                }
                Column(modifier = Modifier.weight(1f)) {
                    secondItem.item()
                }
            }
        }
    }
}
