package dev.mateusz1913.f1results.composable.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import dev.mateusz1913.f1results.desktop.composable.cursorForHorizontalResize
import org.jetbrains.compose.splitpane.ExperimentalSplitPaneApi
import org.jetbrains.compose.splitpane.HorizontalSplitPane
import org.jetbrains.compose.splitpane.rememberSplitPaneState

@OptIn(ExperimentalSplitPaneApi::class)
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
            val splitterState = rememberSplitPaneState(initialPositionPercentage = 1f)
            HorizontalSplitPane(splitPaneState = splitterState) {
                first(350.dp) {
                    firstItem.item()
                }
                second(350.dp) {
                    secondItem.item()
                }
                splitter {
                    visiblePart {
                        Box(
                            Modifier
                                .width(1.dp)
                                .fillMaxHeight()
                                .background(MaterialTheme.colors.background)
                        )
                    }
                    handle {
                        Box(
                            Modifier
                                .markAsHandle()
                                .cursorForHorizontalResize()
                                .background(SolidColor(Color.Gray), alpha = 0.50f)
                                .width(9.dp)
                                .fillMaxHeight()
                        )
                    }
                }
            }
        }
    }
}
