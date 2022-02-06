package dev.mateusz1913.f1results.android.presentation.preview.common

import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import dev.mateusz1913.f1results.composable.common.DropdownButton

@Preview(showBackground = true)
@Composable
fun DropdownButtonPreview() {
    var selectedItem by remember { mutableStateOf("2022") }
    DropdownButton(
        items = listOf("2022", "2021", "2020"),
        selected = selectedItem,
        onSelectedChange = { newSelected -> selectedItem = newSelected }
    )
}
