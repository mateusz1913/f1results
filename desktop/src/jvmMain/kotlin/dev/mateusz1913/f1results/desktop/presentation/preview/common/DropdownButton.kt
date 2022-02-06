package dev.mateusz1913.f1results.desktop.presentation.preview.common

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.*
import dev.mateusz1913.f1results.composable.common.DropdownButton

@Preview
@Composable
fun DropdownButtonPreview() {
    var selectedItem by remember { mutableStateOf("2022") }
    DropdownButton(
        items = listOf("2022", "2021", "2020"),
        selected = selectedItem,
        onSelectedChange = { newSelected -> selectedItem = newSelected }
    )
}
